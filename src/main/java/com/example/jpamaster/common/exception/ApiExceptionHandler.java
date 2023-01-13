package com.example.jpamaster.common.exception;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.flight.exception.BadRequestException;
import com.example.jpamaster.flight.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@ApiOperation(value = "예외 처리", hidden = true)
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ApiResponse<Void> handler(BadRequestException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(e.getHttpStatusCode(), e.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ApiResponse<Void> handler(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(e.getHttpStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = KakaoResException.class)
    public ApiResponse<Void> kakaoErrorHandler(KakaoResException e)
    {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(e.getHttpStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiResponse<Void> handler(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.createError(
                HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage()
        );
    }
}
