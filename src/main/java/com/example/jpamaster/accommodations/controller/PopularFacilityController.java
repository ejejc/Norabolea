package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.FacilittyDto;
import com.example.jpamaster.accommodations.service.PopularFacilityService;
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
@RequestMapping("/facility")
@Api(tags = {"인기 시설 Controller"})
public class PopularFacilityController {

    private final PopularFacilityService popularFacilityService;

    @PostMapping()
    @ApiOperation(value = "인기 시설 등록 API")
    public ApiResponse<Void> add(@RequestBody FacilittyDto facilittyDto) {
        popularFacilityService.saveFacility(facilittyDto.toEntity());
        return ApiResponse.createOk(null);
    }
}
