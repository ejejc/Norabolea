package com.example.jpamaster.flight.web.dto.req;

import lombok.Getter;

@Getter
public class KeywordSearchConditionDto {
    private String keyword = "";

    public void setKeyword(String keyword) {
        this.keyword = keyword == null || keyword.isEmpty() ? "" : keyword.toLowerCase().trim();
    }
}
