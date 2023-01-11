package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.service.ReviewService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
@Api(tags = {"리뷰 Controller"})
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiOperation(value = "리뷰 등록 API")
    public ApiResponse<Void> add(@RequestBody ReviewDto.ReqRes reviewDto) {
        // TODO: 유효한 룸 seq인지 확인 필요
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
    public ApiResponse<Page<ReviewDto.ReqRes>> searchReviewList(@RequestParam(value = "accommodationSeq") Long accommodationSeq
          , @RequestParam(value = "roomSeq", required = false) Long roomSeq, Pageable pageable) {
        return ApiResponse.createOk(reviewService.searchReviewList(accommodationSeq, roomSeq, pageable));
    }

}
