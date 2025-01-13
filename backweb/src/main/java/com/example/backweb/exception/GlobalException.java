package com.example.backweb.exception;

import com.example.backweb.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = UnauthorizedException.class)
    public Result handler(UnauthorizedException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return new Result().setCode(402).setMsg("无权限操作");
    }
    @ExceptionHandler(value = ExpiredCredentialsException.class)
    public Result handler(ExpiredCredentialsException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return new Result().setCode(401).setMsg("登录已过期，请重新登录");
    }
    @ExceptionHandler(value = UnauthenticatedException.class)
    public Result handler(UnauthenticatedException e) {
        log.error("运行时异常：----------------{}", e);
        return new Result().setCode(401).setMsg("未登录");
    }
}
