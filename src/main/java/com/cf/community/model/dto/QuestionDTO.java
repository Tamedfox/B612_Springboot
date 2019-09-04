package com.cf.community.model.dto;

import com.cf.community.model.Question;
import com.cf.community.model.Tag;
import com.cf.community.model.User;
import lombok.Data;

import java.util.List;

/**
 * 返回问题DTO
 */
@Data
public class QuestionDTO {

    //问题
    private Long id;

    private String title;

    private String description;

//    private Long creator;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

//    private String tag;

    //提问人
    private UserDTO userDTO;

    //问题标签信息
    private List<Tag> tags;

}
