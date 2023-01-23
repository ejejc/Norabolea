package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.AnswerDto;
import com.example.jpamaster.accommodations.service.AnswerService;
import com.example.jpamaster.common.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping()
    @ApiOperation(value = "리뷰 답변 등록 API")
    public ApiResponse<Void> addAnswer(@RequestBody AnswerDto.Req req) {
        answerService.addAnswer(req);
        return ApiResponse.createEmptyBody();
    }
}
