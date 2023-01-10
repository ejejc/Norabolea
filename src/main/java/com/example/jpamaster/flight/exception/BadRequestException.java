package com.example.jpamaster.flight.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class BadRequestException extends CommonException {

    public BadRequestException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public BadRequestException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
