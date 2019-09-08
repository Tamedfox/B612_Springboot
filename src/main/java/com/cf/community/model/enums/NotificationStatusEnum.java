package com.cf.community.model.enums;

/**
 * 通知状态类型枚举类
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1);

    private Integer status;

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
