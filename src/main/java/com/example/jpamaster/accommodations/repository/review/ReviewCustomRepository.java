package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {
    List<Review> findAllReviewByRoomSeq(Long roomSeq);

    List<ReviewDto.ReviewSum> findAvgEachScore();

    Page<Review> findAllReviewByRoomList(List<Long> roomSeqList, Pageable pageable);
}
