package com.cf.community.model.dto;

import com.cf.community.model.UserDetail;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private Long id;

    private String username;

//    private String password;

    private Long cmtCreate;

    private String avatarUrl;

    private String nickname;

//    private Integer state;

//    private Long role;
    private UserDetail userDetail;


}
