package com.cf.community.model;

import lombok.Data;
import javax.persistence.*;

/**
 * 站点通知数据层
 */

@Entity
@Data
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long gmtCreate;
    private Integer status;
    private Long creator;

}
