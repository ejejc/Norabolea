package com.example.jpamaster.common.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class JpaMasterNotFoundException extends CommonException {

    public JpaMasterNotFoundException(String message) {
        super(HttpStatusCode.NOT_FOUND, message);
    }
}
