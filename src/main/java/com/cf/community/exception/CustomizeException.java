package com.cf.community.exception;

/**
 * 自定义runtime异常
 */
public class CustomizeException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomizeException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
