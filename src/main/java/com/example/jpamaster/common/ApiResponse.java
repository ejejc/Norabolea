package com.example.jpamaster.common;

import com.example.jpamaster.common.enums.HttpStatusCode;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
//@JsonInclude(value = Include.NON_NULL)
@Getter
@ApiModel(value = "공통 응답 SPEC")
public class ApiResponse<T> {
   private T data;
   private Meta meta;

   @AllArgsConstructor
   @Getter
   @ApiModel(value = "META SPEC")
   public static class Meta {
       private int status;
       private String code;
       private String message;
       private LocalDateTime createdAt;

   }

    private ApiResponse(final T data, final HttpStatusCode code) {
        this.data = data;
        this.meta = new Meta(
                code.getStatus(),
                code.getCode(),
                code.getDefaultMessage(),
                LocalDateTime.now()
        );
    }

    private ApiResponse(final HttpStatusCode code, final String message, final LocalDateTime createdAt) {
        this.meta = new Meta(
                code.getStatus(),
                code.getCode(),
                message,
                createdAt
        );
    }

    /**
     * API 성공 시, 공통 Response 정의
     * @param data
     * @return
     * @param <T>
     */
    public static<T> ApiResponse<T> createOk(final T data) {
        return new ApiResponse<>(data, HttpStatusCode.OK);
    }

    public static<T> ApiResponse<T> createError(final HttpStatusCode code, final String message, final LocalDateTime createdAt) {
        return new ApiResponse<>(code, message, createdAt);
    }
}