package com.example.jpamaster.flight.web;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.service.AirlineService;
import com.example.jpamaster.flight.web.dto.req.AirlineUpdateRequestDto;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/airline")
@RequiredArgsConstructor
@RestController
public class AirlineController {

    private final AirlineService airlineService;

    @DeleteMapping("/{airlineSeq}")
    public ApiResponse<Void> deleteAirline(
            @PathVariable("airlineSeq") Long airlineSeq
    ) {
        airlineService.deleteAirline(airlineSeq);
        return ApiResponse.createOk(null);
    }

    @PutMapping("/{airlineSeq}")
    public ApiResponse<Long> updateAirlineInfo(
            @PathVariable("airlineSeq") Long airlineSeq,
            @RequestBody AirlineUpdateRequestDto dto,
            @RequestPart(name = "airlineImage", required = false) MultipartFile airlineImage
    ) {
        return ApiResponse.createOk(airlineService.updateAirlineInfo(airlineSeq, dto, airlineImage));
    }

    @GetMapping
    public ApiResponse<Page<AirlineDto>> getAirlineList(
            KeywordSearchConditionDto airlineSearchCondition,
            Pageable pageable
    ) {
        Page<AirlineDto> pagedAirline = airlineService.getAirlineListByCondition(airlineSearchCondition, pageable);
        return ApiResponse.createOk(pagedAirline);
    }
}
