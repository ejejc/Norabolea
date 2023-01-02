package com.example.jpamaster.common;

import com.example.jpamaster.common.enums.Status;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
       private Status code;
       private String msg;
   }

    public ApiResponse(T data) {
        this.data = data;
        this.meta = new Meta(Status.OK, Status.OK.getMsg());
    }

    public ApiResponse(Status code) {
        this.meta = new Meta(code, code.getMsg());
    }

    public ApiResponse(Status code, String msg) {
        this.meta = new Meta(code, msg);
    }

    /**
     * API 성공 시, 공통 Response 정의
     * @param data
     * @return
     * @param <T>
     */
    public static<T> ApiResponse<T> createOk(T data) {
        return new ApiResponse<>(data);
    }
}