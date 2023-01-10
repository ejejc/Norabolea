package com.example.jpamaster.common.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class InvalidParameterException extends CommonException {

    public InvalidParameterException(String message) {
        super(HttpStatusCode.INVALID_PARAMETER, message);
    }
}
