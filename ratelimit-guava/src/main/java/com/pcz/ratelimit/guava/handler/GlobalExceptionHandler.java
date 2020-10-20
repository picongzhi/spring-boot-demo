package com.pcz.ratelimit.guava.handler;

import cn.hutool.core.lang.Dict;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author picongzhi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Dict handler(RuntimeException e) {
        return Dict.create().set("msg", e.getMessage());
    }
}
