package com.cf.community.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //标签分类，今后做扩展
    private Long type;

}
