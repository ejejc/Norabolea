package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.accommodations.service.FeaturesService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/features")
@Api(tags = {"특징 Controller"})
public class FeaturesController {

    private final FeaturesService featuresService;

    @PostMapping
    @ApiOperation(value = "특징 등록 API")
    public ApiResponse<Void> add(@RequestBody FeaturesDto.Feature featureDto) {
        featuresService.add(featureDto);
        return ApiResponse.createEmptyBody();
    }

}
