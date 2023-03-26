package com.example.jpamaster.common.security.oauth2;

import com.example.jpamaster.common.enums.HttpStatusCode;
import lombok.Getter;

@Getter
public class OAuth2AuthenticationProcessingException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    public OAuth2AuthenticationProcessingException(final String msg, final HttpStatusCode httpStatusCode) {
        super(msg);
        this.httpStatusCode = httpStatusCode;
    }
}
