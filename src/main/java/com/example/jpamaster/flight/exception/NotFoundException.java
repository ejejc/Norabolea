package com.example.jpamaster.flight.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class NotFoundException extends CommonException {

    public NotFoundException(String message) {
        super(HttpStatusCode.NOT_FOUND, message);
    }
}
