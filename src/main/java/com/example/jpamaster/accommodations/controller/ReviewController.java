package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.service.ReviewService;
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
@RequestMapping("/review")
@Api(tags = {"리뷰 Controller"})
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiOperation(value = "리뷰 등록 API")
    public ApiResponse<Void> add(@RequestBody ReviewDto.Req reviewDto) {
        reviewService.addReview(reviewDto);
        return ApiResponse.createOk(null);
    }

    // 특정 숙소에 대한 리뷰 리스트 불러오기
    // 방 필터 선택 시, 방마다 필터 걸어서 불러오기

}
