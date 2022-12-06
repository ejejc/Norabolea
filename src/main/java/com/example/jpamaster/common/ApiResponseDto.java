package com.example.jpamaster.common;

import com.example.jpamaster.common.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ApiResponseDto<T> {

    private T data;
   private Meta meta;

   @AllArgsConstructor
   public static class Meta {
       private Status code;
       private String msg;
   }

    public ApiResponseDto(T data) {
        this.data = data;
        this.meta = new Meta(Status.OK, Status.OK.getMsg());
    }

    public ApiResponseDto(Status code, String msg) {
        this.meta = new Meta(code, msg);
    }
}