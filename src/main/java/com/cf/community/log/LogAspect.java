package com.cf.community.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {


    @Autowired
    private HttpServletRequest request;


    /**
     * 拦截控制层所有的public方法
     */
    @Pointcut("execution (public * com.cf.community.controller.*.*(..))")
    public void log(){
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object obj = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("The method name {} from url {} request methods {} args {} return value {} time {}"
                ,joinPoint.getSignature(),request.getRequestURI(),request.getMethod(), Arrays.asList(joinPoint.getArgs()),obj,end - start);

        return obj;
    }
}
