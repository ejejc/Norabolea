package com.example.jpamaster.flight.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/airplane")
@RequiredArgsConstructor
@RestController
public class AirplaneController {

    @PostMapping
    public void registerAirplane() {

    }

    @DeleteMapping
    public void deleteAirplane() {

    }

    // csv, 엑셀 파일 형식 벌크 업데이트 지원?
//    @PostMapping
//    public void registerAirplaneBulk() {
//    }
}
