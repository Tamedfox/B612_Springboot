package com.cf.community.model.dto;

import lombok.Data;

/**
 * 通知传输实体类
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String type;

}
