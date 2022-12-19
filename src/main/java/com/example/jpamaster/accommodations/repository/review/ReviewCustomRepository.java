package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.Review;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> findAllReviewByRoomSeq(Long roomSeq);
}
