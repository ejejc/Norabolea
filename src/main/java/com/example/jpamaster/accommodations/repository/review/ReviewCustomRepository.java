package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> findAllReviewByRoomSeq(Long roomSeq);

    List<ReviewDto.ReviewSummary> findAvgEachScore();
}
