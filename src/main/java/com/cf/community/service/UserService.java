package com.cf.community.service;

import com.cf.community.dao.RoleDao;
import com.cf.community.dao.UserDao;
import com.cf.community.model.Role;
import com.cf.community.model.User;
import com.cf.community.model.dto.LoginBodyDTO;
import com.cf.community.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleServcie roleServcie;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * 新增
     * @param user
     */
    public void add(User user){
        //加密密码
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCmtCreate(System.currentTimeMillis());
        userDao.save(user);
    }

    /**
     * 根据用户名对比密码
     * @param loginBodyDTO
     * @return
     */
    public User findByUsername(LoginBodyDTO loginBodyDTO){
        User user = userDao.findByUsername(loginBodyDTO.getUsername());
        if( user == null || !encoder.matches(loginBodyDTO.getPassword(),user.getPassword())){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
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

    public Map login(LoginBodyDTO loginBodyDTO) {
        //验证用户名或密码
        User userInDB = this.findByUsername(loginBodyDTO);
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

        //后期存入redis

        return map;
    }

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
}
