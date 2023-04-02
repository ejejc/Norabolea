package com.example.jpamaster.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HttpStatusCode {

    // 2xx
    OK(200, "SU000", "OK"),


    // 4xx
    BAD_REQUEST(400, "CL000", "Bad Request"),
    UNAUTHORIZED(401, "CL001", "Unauthorized"),
    FORBIDDEN(403, "CL003", "No Atuthentification"),
    INVALID_PARAMETER(422, "CL022", "invalid parameter"),
    NOT_FOUND(404, "CL004", "Not Found"),

    // 5xx
    INTERNAL_SERVER_ERROR(500, "SE000", "Internal Server Error"),

    ;
    private final int status;
    private final String code;
    private final String defaultMessage;

    public static HttpStatusCode findStatusCode(int status) {
        return Arrays.stream(HttpStatusCode.values())
              .filter(vo -> vo.status == status)
              .findFirst()
              .orElse(null);
    }
}