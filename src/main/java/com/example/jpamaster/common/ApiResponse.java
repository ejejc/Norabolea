package com.example.jpamaster.common;

import com.example.jpamaster.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
//@JsonInclude(value = Include.NON_NULL)
@Getter
public class ApiResponse<T> {

    private T data;
   private Meta meta;

   @AllArgsConstructor
   @Getter
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

    public static<T> ApiResponse<T> createOk(T data) {
        return new ApiResponse<>(data);
    }
}