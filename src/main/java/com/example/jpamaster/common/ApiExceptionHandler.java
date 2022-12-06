package com.example.jpamaster.common;

import com.example.jpamaster.common.ApiResponseDto.Meta;
import com.example.jpamaster.common.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ApiResponseDto testException(Exception e) {
        return new ApiResponseDto(Status.OK, "실패");
    }
}
