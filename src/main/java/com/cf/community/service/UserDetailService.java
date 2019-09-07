package com.cf.community.service;

import com.cf.community.dao.UserDao;
import com.cf.community.dao.UserDetailDao;
import com.cf.community.model.User;
import com.cf.community.model.UserDetail;
import com.cf.community.model.dto.UserDTO;
import com.cf.community.model.dto.UserDetailDTO;
import com.cf.community.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户详细信息服务层
 */
@Service
public class UserDetailService {

    @Autowired
    private UserDetailDao userDetailDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;


    /**
     * 新增用户详细
     * @param userDetail
     */
    public void add(UserDetail userDetail){
        userDetailDao.save(userDetail);
    }

    /**
     * 根据逻辑主键查询
     * @return
     */
    public UserDTO findOne() {
        String username = jwtUtil.getUsernameFromRequest(request);
        User user = userDao.findByUsername(username);
        UserDetail userDetail = userDetailDao.findById(user.getDetail()).get();
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        userDTO.setUserDetail(userDetail);
        return userDTO;
    }

    @Transactional
    public void updateDetail(UserDetailDTO userDetailDTO) {
        String username = jwtUtil.getUsernameFromRequest(request);
        User user = userDao.findByUsername(username);
        UserDetail userDetail = userDetailDao.findById(user.getDetail()).get();
        BeanUtils.copyProperties(userDetailDTO,user);
        BeanUtils.copyProperties(userDetailDTO,userDetail);
        userDetail.setEMail(userDetailDTO.getEmail());
        userDao.save(user);
        userDetailDao.save(userDetail);
    }
}
