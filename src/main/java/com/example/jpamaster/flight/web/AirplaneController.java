package com.example.jpamaster.flight.web;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.service.AirplaneService;
import com.example.jpamaster.flight.web.dto.req.AirplaneRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/{airlineSeq}/airplane")
@RequiredArgsConstructor
@RestController
public class AirplaneController {

    private final AirplaneService airplaneService;

    @PostMapping
    public ApiResponse<Void> registerAirplane (
            @PathVariable("airlineSeq") Long airlineSeq,
            @RequestBody AirplaneRegisterRequestDto dto
    ) {
        airplaneService.registerAirplane(airlineSeq, dto);
        return ApiResponse.createEmptyBody();
    }
//
//    @DeleteMapping
//    public void deleteAirplane (
//            @PathVariable("airlineSeq") Long airlineSeq
//    ) {
//
//    }

    // csv, 엑셀 파일 형식 벌크 업데이트 지원?
//    @PostMapping
//    public void registerAirplaneBulk() {
//    }
}
