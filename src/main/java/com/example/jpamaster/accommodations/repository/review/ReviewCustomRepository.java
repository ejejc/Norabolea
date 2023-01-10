package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.dto.ReviewDto;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> findAllReviewByRoomSeq(Long roomSeq);

    List<ReviewDto.ReviewSum> findAvgEachScore();

    List<Review> findAllReviewByRoomList(List<Long> roomseqList);
}
