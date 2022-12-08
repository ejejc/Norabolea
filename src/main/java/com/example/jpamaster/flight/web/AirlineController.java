package com.example.jpamaster.flight.web;

import com.example.jpamaster.flight.service.AirlineService;
import com.example.jpamaster.flight.web.dto.req.AirlineRequestDto;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/airline")
@RequiredArgsConstructor
@RestController
public class AirlineController {

    private final AirlineService airlineService;

    @DeleteMapping("/{airlineSeq}")
    public ResponseEntity<Void> deleteAirline(
            @PathVariable("airlineSeq") Long airlineSeq
    ) {
        airlineService.deleteAirline(airlineSeq);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{airlineSeq}")
    public ResponseEntity<Long> updateAirlineInfo(
            @PathVariable("airlineSeq") Long airlineSeq,
            @RequestBody AirlineRequestDto dto
    ) {
        return ResponseEntity.ok().body(airlineService.updateAirlineInfo(airlineSeq, dto));
    }

    @GetMapping
    public ResponseEntity<Page<AirlineDto>> getAirlineList(
            KeywordSearchConditionDto airlineSearchCondition,
            Pageable pageable
    ) {
        Page<AirlineDto> pagedAirline = airlineService.getAirlineListByCondition(airlineSearchCondition, pageable);
        return ResponseEntity.ok().body(pagedAirline);
    }



}
