package com.cf.community.model.entity;

import com.cf.community.exception.CustomizeException;
import com.cf.community.exception.ErrorCode;
import lombok.Data;

/**
 * 返回结果实体
 */

@Data
public class Result<T> {

    //返回码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;

    public Result() {
    }

    /**
     * 操作错误，根据ErrorCode填写
     * @param errorCode
     * @return
     */
    public static Result errorOf(ErrorCode errorCode) {
        Result result = new Result();
        result.code = errorCode.getCode();
        result.message = errorCode.getMessage();
        return result;
    }

    /**
     * 无数据操作成功
     * @return
     */
    public static Result okOf() {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    /**
     * 带数据操作成功
     * @param data
     * @return
     */
    public static <T> Result okOf(T data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("操作成功");
        result.data = data;
        return result;
    }


    public static Result errorOf(String message) {
        Result result = new Result();
        result.setCode(4000);
        result.setMessage(message);
        return result;
    }

    public static Result errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    private static Result errorOf(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
