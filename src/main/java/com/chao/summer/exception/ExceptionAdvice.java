package com.chao.summer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

//    @ExceptionHandler({Exception.class})
    public String handleSummerException(Exception e) {
        log.error("Summer Exception: ", e);
        return "testArrayIndexOutOfBoundsException";
    }
}

