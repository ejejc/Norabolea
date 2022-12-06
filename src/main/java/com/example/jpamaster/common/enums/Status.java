package com.example.jpamaster.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    OK("요청에 성공하였습니다.");

    private final String msg;
}
