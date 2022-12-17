package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public void add(@RequestBody ReviewDto.Req reviewDto) {
        reviewService.addReview(reviewDto);
    }


}
