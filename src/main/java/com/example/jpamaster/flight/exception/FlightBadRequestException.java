package com.example.jpamaster.flight.exception;


import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.common.exception.CommonException;
import lombok.Getter;

@Getter
public class FlightBadRequestException extends CommonException {

    public FlightBadRequestException(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public FlightBadRequestException(HttpStatusCode httpStatusCode, String message) {
        super(httpStatusCode, message);
    }
}
