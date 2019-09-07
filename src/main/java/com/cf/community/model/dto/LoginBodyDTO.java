package com.cf.community.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录DTO
 */
@Data
public class LoginBodyDTO implements Serializable {

    private String username;
    private String password;

}
