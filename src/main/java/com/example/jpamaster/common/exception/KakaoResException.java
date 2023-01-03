package com.example.jpamaster.common.exception;

import lombok.Getter;

@Getter
public class KakaoResException extends RuntimeException{
    private String message;
    private String errorType;

    public KakaoResException() {
    }

    public KakaoResException(String message, String errorType) {
        this.message = message;
        this.errorType = errorType;
    }
}
