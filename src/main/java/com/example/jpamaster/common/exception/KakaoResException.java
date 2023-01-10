package com.example.jpamaster.common.exception;

import com.example.jpamaster.common.enums.HttpStatusCode;
import lombok.Getter;

@Getter
public class KakaoResException extends CommonException{
    private String errorType;

    public KakaoResException(String message, String errorType, HttpStatusCode code) {
        super(code, message);
        this.errorType = errorType;
    }
}
