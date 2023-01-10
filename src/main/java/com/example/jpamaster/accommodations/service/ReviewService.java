package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.dto.ReviewDto.Req;
import com.example.jpamaster.common.exception.InvalidParameterException;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    public ApiResponse<ReviewDto.ReviewSummary> searchReviewAvgGrade(Long accommodationSeq, Long roomSeq) {

        if (Objects.isNull(accommodationSeq) || accommodationSeq == 0L) {
            throw new InvalidParameterException("유효하지 않은 숙소입니다.");
        }

        List<Room> roomList = roomReposittory.findByAccommodationSeq(accommodationSeq);
        if (Objects.nonNull(roomSeq)) {
            roomList = roomList.stream()
                    .filter(vo -> vo.getRoomSeq().equals(roomSeq))
                    .collect(Collectors.toList());
        }
        return ApiResponse.createOk(this.findReviewForAccommodation(roomList));

    }

    private ReviewDto.ReviewSummary findReviewForAccommodation(List<Room> roomList) {
        List<ReviewDto.ReviewSum> reviewList = reviewRepository.findAvgEachScore();
        reviewList = reviewList.stream()
                .filter(vo -> roomList.stream().anyMatch(vo2 -> vo.getRoomSeq().equals(vo2.getRoomSeq())))
                .collect(Collectors.toList());

        ReviewDto.ReviewSummary reviewSummary = new ReviewDto.ReviewSummary();
        for (ReviewDto.ReviewSum reviewSum : reviewList) {
            reviewSummary.sum(reviewSum);
        }
        reviewSummary.avg();
        return reviewSummary;
    }

    public ApiResponse<Void> searchReviewList(Long accommodationSeq, Long roomSeq) {
        if (Objects.isNull(accommodationSeq) || accommodationSeq == 0L) {
            throw new InvalidParameterException("유효하지 않은 숙소입니다.");
        }
        List<Room> roomList = roomReposittory.findByAccommodationSeq(accommodationSeq);
        List<Long> roomSeqList = roomList.stream()
                .map(Room::getRoomSeq).collect(Collectors.toList());
        List<Review> reviewList = reviewRepository.findAllReviewByRoomList(roomSeqList);
        List<ReviewDto.Req> reviewDtoList = reviewList.stream()
                  .map(ReviewDto.Req::changeToDto)
                  .collect(Collectors.toList());
        System.out.println("확인");
        // 평균 별점, 객실명, 인기시설, 리뷰 내용, 리뷰 사진, 등록일자
        return null;
    }
}
