package com.cf.community.exception;

public enum ErrorCode {
    SUCCESS(200,"操作成功"),
    ACCESS_DENIED(403,"权限不足，请登录" ),
    LOGIN_ERROR(1001,"用户名或密码错误" ),
    SERVICE_ERROR(1002,"服务器长满了猴面包树，需要清理一下，请稍后再来" ),
    METHOD_ERROR(1003,"请求方式错误" ),
    NOT_FOUND(1004,"未找到相关资源" ),
    PARAMS_ERROR(1005,"请求参数错误" ),
    FILE_FORMAT_ERROR(1006,"文件格式错误" ),
    UPLOAD_FAIL(1007,"写入文件失败" );

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
