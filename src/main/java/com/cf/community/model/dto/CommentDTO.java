package com.cf.community.model.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

//    private Long parentId;

    private Integer type;

//    private Long commentator;

    private Long gmtCreate;

    private Integer likeCount;

    private String content;

    private UserDTO userDTO;

}
