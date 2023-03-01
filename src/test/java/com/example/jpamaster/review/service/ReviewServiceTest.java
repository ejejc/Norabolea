package com.example.jpamaster.review.service;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.accommodations.service.ReviewService;
import com.example.jpamaster.accommodations.service.RoomService;
import com.example.jpamaster.review.repository.ReviewFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    RoomService roomService;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    RoomReposittory roomReposittory;

    @InjectMocks
    ReviewService reviewService;

    List<Review> reviewList = new ArrayList<>();

    @BeforeEach
    public void ofSet() {
        reviewList.addAll(ReviewFixture.generateReviewList());
    }

    @Test
    @DisplayName("숙소에 대한 각 별정의 평균 점수가 잘 구해지는지 확인")
    public void getReviewAvgGradeTest() {
        // given
        when(reviewRepository.findEachPartScore(anyList())).thenReturn(new ReviewDto.ReviewSum(2,4,6,8,2L));
        // when
        ReviewDto.ReviewSummary reviewSummary = reviewService.findReviewForAccommodation(anyList());
        // then
        Assertions.assertThat(reviewSummary.getCleanlinessAvg()).isEqualTo(1.0);
        Assertions.assertThat(reviewSummary.getConvenienceAvg()).isEqualTo(2.0);
        Assertions.assertThat(reviewSummary.getKindnessAvg()).isEqualTo(3.0);
        Assertions.assertThat(reviewSummary.getLocationAvg()).isEqualTo(4.0);
    }

    /**
     * 알게된 점: 테스트를 위해 setter가 사용되어야 할 경우, 테스트 코드를 위한 별도 메소드를 생성해주는 것이 더 올바르다.
     * 서비스 내, private 메소드일 경우 별도 테스트 코드를 하지 않고,
     * 호출하는 서비스 메소드에서 같이 테스트 해도 잘 되기 때문에 private -> public으로 변경하지 말자 !!
     */
    @Test
    @DisplayName("최대 3개까지만 등록되는 베스트 리뷰가 잘 등록되는지 확인")
    public void addBestReivewTes() {
        // given
        when(roomReposittory.findByAccommodationSeq(anyLong())).thenReturn(Collections.emptyList());
        when(reviewRepository.findAllReviewByRoomListToBest(anyList())).thenReturn(reviewList);
        when(reviewRepository.findById(anyLong())).thenReturn(reviewList.stream().filter(vo->!vo.isBestYn()).findFirst());
        // when
        ReviewDto.BestReq reviewDto = new ReviewDto.BestReq();
        reviewDto.ofTestSeq(1L, 2L);
        reviewService.addBestReview(reviewDto);
        // then
        Review result = new Review();
        for (Review review : reviewList ) {
            if (review.getContent().equals("리뷰1")) {
                result = review;
                break;
            }
        }
        Assertions.assertThat(result.isBestYn()).isTrue();
    }

    @Test
    @DisplayName("베스트 리뷰가 3개일 때, 가장 마지막은 삭제되고 새로운 리뷰가 베스트 리뷰가 되는지 확인")
    public void addBestReviewMaxThreeCntTest() {}
}
