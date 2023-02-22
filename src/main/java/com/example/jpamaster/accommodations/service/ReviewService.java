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
import org.springframework.transaction.annotation.Transactional;

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
        ReviewDto.ReviewSum reviewSums
                = reviewRepository.findEachPartScore(roomList.stream().map(Room::getRoomSeq).collect(Collectors.toList()));
        if (Objects.isNull(reviewSums)) {
            throw new InvalidParameterException("dkf");
        }
        return ReviewDto.ReviewSummary.makeSummary(reviewSums);
    }

    public Page<ReqRes> searchReviewList(ReqRes req, Pageable pageable) {
        List<Room> roomList = roomService.searchRoomListOfAccommodationSeq(req.getAccommodationSeq());
        if (Objects.nonNull(req.getRoomSeq())) {
            roomList = roomList.stream().filter(vo -> vo.getRoomSeq().equals(req.getRoomSeq())).collect(Collectors.toList());
        }
        Page<Review> reviewList = reviewRepository.findAllReviewByRoomListForPaging(
            roomList.stream().map(Room::getRoomSeq).collect(Collectors.toList()), pageable, req
        );
        return reviewList.map(ReqRes::changeToDto);
    }


    @Transactional // TODO: transactional을 해줘야지만 update 쿼리가 날아가는 이유 공부하기 : 트랜잭션 안에 있어야 트랜잭션 commit 후 flush가 발생되기 때문
    public void addBestReview(ReviewDto.BestReq bestReq) {
        // 특정 숙소에 해당하는 review List 가져오기
        List<Review> reviewList = this.searchReviewListForAccommodation(bestReq.getAccommodationSeq());

        if (reviewList.size() >= 3) {
            Review review = reviewRepository.findById(reviewList.get(0).getSeq())
                    .orElseThrow(() -> new InvalidParameterException("존재하지 않는 리뷰 입니다."));
            review.updateBestFalse();
        }
        Review addBestReview = reviewRepository.findById(bestReq.getReviewSeq())
                .orElseThrow(() -> new InvalidParameterException("존재하지 않는 리뷰 입니다."));
        addBestReview.updateBestTrue();
    }

    private List<Review> searchReviewListForAccommodation(Long accommodationSeq) {
        List<Long> roomList = roomReposittory.findByAccommodationSeq(accommodationSeq)
                .stream().map(Room::getRoomSeq).collect(Collectors.toList());
        return reviewRepository.findAllReviewByRoomListToBest(roomList);
    }
}
