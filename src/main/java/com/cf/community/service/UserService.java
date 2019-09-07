package com.cf.community.service;

import com.cf.community.dao.RoleDao;
import com.cf.community.dao.UserDao;
import com.cf.community.dao.UserRoleDao;
import com.cf.community.model.Role;
import com.cf.community.model.User;
import com.cf.community.model.UserRole;
import com.cf.community.model.dto.LoginBodyDTO;
import com.cf.community.model.dto.UserDTO;
import com.cf.community.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleServcie roleServcie;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 更新头像
     * @param url
     */
    @Transactional
    public void updateAvatarUrl(String url) {
        String username = jwtUtil.getUsernameFromRequest(request);
        User user = userDao.findByUsername(username);
        user.setAvatarUrl(url);
        userDao.save(user);
    }

    /**
     * 新增
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(User user){
        //加密密码
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCmtCreate(System.currentTimeMillis());
//        user.setRole(1L);//设置为普通用户
        user.setCmtCreate(System.currentTimeMillis());
        user.setAvatarUrl("https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg");//设置默认头像
        user.setState(1); //账号状态，默认1，正常

        User saveUser = userDao.save(user);

        //设置user_role表,默认权限为普通user
        UserRole userRole = new UserRole();
        userRole.setUserid(saveUser.getId());
        userRole.setRoleid(1L);
        userRoleDao.save(userRole);
    }

    /**
     * 根据用户名对比密码
     * @param username
     * @return
     */
    public User findByUseranme(String username){
        User user = userDao.findByUsername(username);
        return user;
    }

    /**
     * 根据username生成UserDetails信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        //查询用户权限
        List<Role> roles = roleServcie.findByUserId(user.getId());

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),"******", authorities);
    }

    /**
     * 登录返回用户信息给前端
     * @param loginBodyDTO
     * @return
     */
    public Map login(LoginBodyDTO loginBodyDTO) {
        //验证用户名或密码
        User userInDB = this.findByUseranme(loginBodyDTO.getUsername());
        //获取UserDetail,包含用户名和角色
        UserDetails userDetails = loadUserByUsername(userInDB.getUsername());
        //签发token
        String token = jwtUtil.generatorToken(userDetails);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        Map<String, Object> map = new HashMap<>();

        map.put("token", token);
        map.put("username", userInDB.getUsername());
        map.put("avatarUrl",userInDB.getAvatarUrl());
        map.put("roles", roles);
        map.put("nickname", userInDB.getNickname());

        //后期存入redis

        return map;
    }

    /**
     * 解析token返回用户信息
     * @param token
     * @return
     */
    public UserDetails loadUserByToken(String token) {

        String username = jwtUtil.getUsernameByToken(token);
        //非法token
        if(null == username){
            return null;
        }

        //预留redist取值比较


        List<String> roles = jwtUtil.getRolesByToken(token);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(username, "******", authorities);
    }

    public List<UserDTO> findNewUser(){
        List<User> userList = userDao.findFirst5ByStateOrderByCmtCreateDesc(1);
        List<UserDTO> result = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            result.add(userDTO);
        }
        return result;
    }
}
