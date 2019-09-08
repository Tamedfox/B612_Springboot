package com.cf.community.model.enums;

/**
 * 通知类型枚举类
 */
public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复了你的问题"),
    REPLY_COMMENT(2,"回复了你的评论"),
    LIKE_QUESTION(3,"点赞了你的问题"),
    LIKE_COMMENT(4,"点赞了你的评论");


    private Integer type;
    private String message;

    NotificationEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public static String nameOfType(Integer type){
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if(notificationEnum.getType() == type){
                return notificationEnum.getMessage();
            }
        }
        return "";
    }
}
