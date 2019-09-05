package com.cf.community.model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;

/**
 * 搜索问题实体类
 */
@Data
@Document(indexName = "question", type = "docs")
public class QuestionSearch {

    @Id
    private Long id;

    //标题
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    //问题
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String description;

}
