package com.example.jpamaster.accommodations.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoLocationClient", url = "https://dapi.kakao.com/v2/local/search/address.json")
public interface KakaoFeignClient {

    @GetMapping
    public Object searchLocation(@RequestParam("query") String query);
}
