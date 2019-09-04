package com.cf.community.model.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

//    private String password;

    private Long cmtCreate;

    private String avatarUrl;

    private String nickname;

//    private Integer state;

//    private Long role;

}
