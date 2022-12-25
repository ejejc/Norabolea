package com.example.jpamaster.flight.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class FlightNotFoundException extends CommonException {

    public FlightNotFoundException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public FlightNotFoundException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
