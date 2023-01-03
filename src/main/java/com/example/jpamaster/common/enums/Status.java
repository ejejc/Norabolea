package com.example.jpamaster.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    OK("요청에 성공하였습니다.")
    , FAIL("요청에 실패하였습니다.")
    , INVALID_ACCOMMODATION("유효하지 않은 숙소입니다.");

    private final String msg;
}
