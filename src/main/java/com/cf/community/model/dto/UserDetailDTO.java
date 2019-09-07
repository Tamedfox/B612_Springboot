package com.cf.community.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailDTO {

    private Date birthday;

    private String email;

    private String industry;

    private String nickname;

    private String position;

}
