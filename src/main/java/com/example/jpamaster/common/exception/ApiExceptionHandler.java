package com.example.jpamaster.common.exception;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.flight.exception.FlightBadRequestException;
import com.example.jpamaster.flight.exception.FlightNotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@ApiOperation(value = "예외 처리", hidden = true)
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = FlightBadRequestException.class)
    public ApiResponse<Void> handler(FlightBadRequestException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(e.getHttpStatusCode(), e.getMessage(), e.getCreatedAt());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = FlightNotFoundException.class)
    public ApiResponse<Void> handler(FlightNotFoundException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(e.getHttpStatusCode(), e.getMessage(), e.getCreatedAt());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiResponse<Void> handler(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(
                HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now()
        );
    }
}
