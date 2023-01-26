package com.example.jpamaster.common.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class JpaMasterBadRequest extends CommonException {


    public JpaMasterBadRequest(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }
}
