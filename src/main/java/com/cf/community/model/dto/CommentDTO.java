package com.cf.community.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDTO implements Serializable {

    private Long id;

//    private Long parentId;

    private Integer type;

//    private Long commentator;

    private Long gmtCreate;

    private Integer likeCount;

    private String content;

    private UserDTO userDTO;

}
