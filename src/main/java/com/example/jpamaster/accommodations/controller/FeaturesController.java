package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.accommodations.service.FeaturesService;
import com.example.jpamaster.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/features")
public class FeaturesController {

    private final FeaturesService featuresService;

    @PostMapping
    public ApiResponse<Void> add(@RequestBody FeaturesDto featuresDto) {
        featuresService.add(featuresDto);
        return ApiResponse.createEmptyBody();
    }

}
