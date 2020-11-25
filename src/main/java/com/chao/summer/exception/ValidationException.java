package com.chao.summer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
        log.error("ValidationException", this);
    }
}
