package com.example.jpamaster.flight.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class NotFounException extends CommonException {

    public NotFounException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public NotFounException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
