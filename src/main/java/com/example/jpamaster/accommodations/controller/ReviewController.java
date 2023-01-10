package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.service.ReviewService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/avg/grade")
    @ApiOperation(value = "숙소 리뷰 평균 등급 API")
    public ApiResponse<ReviewDto.ReviewSummary> searchReviewAvgGrade(@RequestParam(value = "accommodationSeq") Long accommodationSeq
            , @RequestParam(value = "roomSeq", required = false) Long roomSeq) {
        return reviewService.searchReviewAvgGrade(accommodationSeq, roomSeq);
    }

    @GetMapping("/list")
    public ApiResponse<Void> searchReviewList(@RequestParam(value = "accommodationSeq") Long accommodationSeq
          , @RequestParam(value = "roomSeq", required = false) Long roomSeq) {
        return reviewService.searchReviewList(accommodationSeq, roomSeq);
    }

}
