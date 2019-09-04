package com.cf.community.exception;

import com.cf.community.model.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 异常处理
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限不足
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result accessDeniedExceptionHandler(Exception e){
        return Result.errorOf(ErrorCode.ACCESS_DENIED);
    }

    public Result httpRequestMehodNotSupportedExceptionHandler(Exception e){
        return Result.errorOf(ErrorCode.METHOD_ERROR);
    }


    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e){
        if(e instanceof CustomizeException){
            return Result.errorOf((CustomizeException)e);
        }
        return Result.errorOf(ErrorCode.SERVICE_ERROR);
    }
}
