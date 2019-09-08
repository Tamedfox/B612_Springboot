package com.cf.community.model;

import lombok.Data;

import javax.persistence.*;


/**
 * 通知实体类
 */
@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long notifier; //通知人id
    private String notifierName; //通知人名称
    private Long receiver;//接收人id
    private Long outerId; // 评论上层主键
    private String outerTitle;//评论上层名称
    private Integer type; //评论或问题下的评论
    private Long gmtCreate;//创建时间
    private Integer status;//是否已读 0-未读 1-已读
}
