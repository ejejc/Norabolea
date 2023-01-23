package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.dto.AnswerDto;
import com.example.jpamaster.accommodations.repository.AnswerRepository;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.common.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService {

    private final ReviewRepository reviewRepository;
    private final AnswerRepository answerRepository;

    public void addAnswer(AnswerDto.Req req) {
        Review review = reviewRepository.findById(req.getReviewSeq())
                .orElseThrow(() -> new InvalidParameterException("유효하지 않는 리뷰 입니다."));
        answerRepository.save(req.toEntity(review));
    }
}
