package com.example.jpamaster.accommodations.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class InvalidAccommodationException extends CommonException {

    public InvalidAccommodationException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public InvalidAccommodationException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
