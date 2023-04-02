package com.example.jpamaster.common.exception;

import com.example.jpamaster.common.enums.HttpStatusCode;
import lombok.Getter;

@Getter
public class NoAuthenticationException extends CommonException {

    public NoAuthenticationException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
