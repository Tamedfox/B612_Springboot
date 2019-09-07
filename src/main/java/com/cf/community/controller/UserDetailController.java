package com.cf.community.controller;

import com.cf.community.model.User;
import com.cf.community.model.UserDetail;
import com.cf.community.model.dto.UserDTO;
import com.cf.community.model.dto.UserDetailDTO;
import com.cf.community.model.entity.Result;
import com.cf.community.service.UserDetailService;
import com.cf.community.service.UserService;
import com.cf.community.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/userDetail")
public class UserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping()
    public Result findOne(){
        UserDTO userDTO = userDetailService.findOne();
        return Result.okOf(userDTO);
    }

    @PutMapping()
    public Result update(@RequestBody UserDetailDTO userDetailDTO){
        userDetailService.updateDetail(userDetailDTO);
        return null;
    }

}
