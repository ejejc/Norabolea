package com.example.jpamaster.common;

import com.example.jpamaster.common.enums.Status;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
@Slf4j
@ApiOperation(value = "예외 처리", hidden = true)
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiResponse<Void> handler(Exception e)
    {
        // 로그
        log.error(e.getCause().getMessage(), e);
        return new ApiResponse<Void>(Status.FAIL);
    }
}
