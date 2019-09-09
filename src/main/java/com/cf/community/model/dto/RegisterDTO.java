package com.cf.community.model.dto;

import lombok.Data;

/**
 * 注册数据传输类
 */

@Data
public class RegisterDTO {

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String code;

}
