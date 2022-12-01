package com.example.jpamaster.flight.web.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.yaml.snakeyaml.util.UriEncoder;

/**
 * dto 의 query string 으로 넘어오는 값을 매핑하기 위해선 setter 가 있어야 값을 매핑해준다.
 * 기본 생성자는 없어도 값을 매핑해준다.
 * setter 가 없다면 null 로 매핑
 * URI 인코딩 된 문자는 디코딩이 된다.
 * 어떻게 생성을 해서 매핑해주는거지?!
 */
@Getter
public class AirportSearchCondition {
    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword == null || keyword.isEmpty() ? "" : keyword.toLowerCase().trim();
    }
}