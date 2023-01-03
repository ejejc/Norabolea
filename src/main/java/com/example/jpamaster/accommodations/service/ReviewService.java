package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto.Req;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.common.enums.Status;
import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RoomReposittory roomReposittory;

    public void addReview(Req reviewDto) {
        Room room = roomReposittory.findById(reviewDto.getRoomSeq()).orElse(null);
        Review review = reviewDto.changeToEntity(room);
        reviewRepository.save(reviewDto.changeToEntity(room));
    }

    public ApiResponse<Void> searchReviewAvgGrade(Long accommodationSeq, Long roomSeq) {
        List<Tuple> reviewList = new ArrayList<>();
        if (Objects.isNull(accommodationSeq) || accommodationSeq == 0L) {
            return new ApiResponse<>(Status.INVALID_ACCOMMODATION);
        }
        List<Room> roomList = roomReposittory.findByAccommodationSeq(accommodationSeq);
        if (Objects.nonNull(roomSeq)) {
            roomList = roomList.stream()
                    .filter(vo -> vo.getRoomSeq().equals(roomSeq))
                    .collect(Collectors.toList());
        }
        for (Room room : roomList) {
            reviewList.addAll(reviewRepository.findAvgEachScore(room.getRoomSeq()));
        }
        return null;
    }
}
