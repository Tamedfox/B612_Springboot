package com.cf.community.controller;

import com.cf.community.exception.ErrorCode;
import com.cf.community.model.User;
import com.cf.community.model.dto.LoginBodyDTO;
import com.cf.community.model.entity.Result;
import com.cf.community.service.UserService;
import com.cf.community.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController()
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     * @param loginBodyDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(LoginBodyDTO loginBodyDTO){
        if(StringUtils.isBlank(loginBodyDTO.getUsername()) | StringUtils.isBlank(loginBodyDTO.getPassword())){
            return Result.errorOf(ErrorCode.LOGIN_ERROR);
        }

        try{
            //验证登录，签发token
            Map map = userService.login(loginBodyDTO);
            return Result.okOf(map);
        }catch (UsernameNotFoundException e){
            return Result.errorOf(ErrorCode.LOGIN_ERROR);
        }catch (RuntimeException e){
            return Result.errorOf(e.getMessage());
        }

    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userService.add(user);
        return Result.okOf();
    }

    /**
     * 推出登录
     * @return
     */
    @GetMapping("/logout")
    public Result logout(){
        //留给以后从redis删除
        return Result.okOf();
    }
}
