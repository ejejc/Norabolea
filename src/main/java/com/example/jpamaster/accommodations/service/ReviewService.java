package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.dto.ReviewDto.ReqRes;
import com.example.jpamaster.common.exception.InvalidParameterException;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RoomReposittory roomReposittory;
    private final RoomService roomService;

    public void addReview(ReqRes reviewDto) {
        Room room = roomReposittory.findById(reviewDto.getRoomSeq()).orElseThrow(() -> new InvalidParameterException("유효하지 않는 룸 입니다."));
        reviewRepository.save(reviewDto.changeToEntity(room));
    }

    public ApiResponse<ReviewDto.ReviewSummary> searchReviewAvgGrade(Long accommodationSeq, Long roomSeq) {
        List<Room> roomList = roomService.searchRoomListOfAccommodationSeq(accommodationSeq);
        if (Objects.nonNull(roomSeq)) {
            roomList = roomList.stream()
                    .filter(vo -> vo.getRoomSeq().equals(roomSeq))
                    .collect(Collectors.toList());
        }
        return ApiResponse.createOk(this.findReviewForAccommodation(roomList));

    }

    private ReviewDto.ReviewSummary findReviewForAccommodation(List<Room> roomList) {
        // roomSeq 별로, 리뷰들의 총점을 가져온다.
        List<ReviewDto.ReviewSum> reviewSums = reviewRepository.findAvgEachScore();
        reviewSums = reviewSums.stream()
                .filter(vo -> roomList.stream().anyMatch(vo2 -> vo.getRoomSeq().equals(vo2.getRoomSeq())))
                .collect(Collectors.toList());

        ReviewDto.ReviewSummary reviewSummary = new ReviewDto.ReviewSummary();
        for (ReviewDto.ReviewSum reviewSum : reviewSums) {
            reviewSummary.sum(reviewSum);
        }
        reviewSummary.avg();
        return reviewSummary;
    }

    public Page<ReqRes> searchReviewList(ReqRes req, Pageable pageable) {
        List<Room> roomList = roomService.searchRoomListOfAccommodationSeq(req.getAccommodationSeq());
        if (Objects.nonNull(req.getRoomSeq())) {
            roomList = roomList.stream().filter(vo -> vo.getRoomSeq().equals(req.getRoomSeq())).collect(Collectors.toList());
        }
        Page<Review> reviewList = reviewRepository.findAllReviewByRoomList(
                roomList.stream().map(Room::getRoomSeq).collect(Collectors.toList()), pageable, req
        );
        // TODO: 숙소별 인기시설도 추가
        return reviewList.map(ReqRes::changeToDto);
    }


}
