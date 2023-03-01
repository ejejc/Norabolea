package com.example.jpamaster.review.repository;

import com.example.jpamaster.accommodation.repository.Fixture;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import com.example.jpamaster.popular_facility.repository.PopularFacilityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ConfiguredDataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PopularFacilityRepository popularFacilityRepository;
    @Autowired
    AccommodationsRepository accommodationsRepository;
    Accommodations accommodations;
    Review review;
    PopularFacility popularFacility;

    @BeforeEach
    public void setUp() {
        popularFacility = PopularFacilityFixture.generatePopularFacility();
        popularFacilityRepository.save(popularFacility);
        accommodations = Fixture.generateAccommodation(List.of(popularFacility));

        review = ReviewFixture.generateReview();
    }
    @Test
    @DisplayName("특정 숙소에 해당하는 각 항목별 별점 합산 조회 확인")
    public void findEachPartScoreTest() {
        // given
        accommodationsRepository.save(accommodations);
        List<Review> reviewList = ReviewFixture.generateReviewList();
        for (Review review : reviewList) {
            review.setRoom(accommodations.getRooms().get(0));
        }
        reviewRepository.saveAll(reviewList);

        // when
        ReviewDto.ReviewSum reviewSum = reviewRepository.findEachPartScore(List.of(accommodations.getRooms().get(0).getRoomSeq()));

        // then
        Assertions.assertThat(reviewSum.getCleanliness()).isEqualTo(reviewList.stream().mapToInt(Review::getCleanlinessStarScore).sum());
        Assertions.assertThat(reviewSum.getConvenience()).isEqualTo(reviewList.stream().mapToInt(Review::getConvenienceStarScore).sum());
        Assertions.assertThat(reviewSum.getKindness()).isEqualTo(reviewList.stream().mapToInt(Review::getKindnessStarScore).sum());
        Assertions.assertThat(reviewSum.getLocation()).isEqualTo(reviewList.stream().mapToInt(Review::getLocationStarScore).sum());
        Assertions.assertThat(reviewSum.getReviewCnt()).isEqualTo(reviewList.size());
    }

    @Test
    @DisplayName("검색 조건이 없을 경우 베스트 리뷰가 처음으로 정렬되는지 확인")
    public void findAllReviewForBestSortTest() {
        // given
        accommodationsRepository.save(accommodations);
        List<Review> reviewList = ReviewFixture.generateReviewList();
        for (Review review : reviewList) {
            review.setRoom(accommodations.getRooms().get(0));
        }
        reviewRepository.saveAll(reviewList);
        List<Long> list = List.of(accommodations.getRooms().get(0).getRoomSeq());
        // when
        Page<Review> reviews = reviewRepository.findAllReviewByRoomListForPaging(list, PageRequest.of(0, 20),new ReviewDto.ReqRes());
        // then
        Assertions.assertThat(reviews.getContent().get(0).isBestYn()).isTrue();
        Assertions.assertThat(reviews.getContent().size()).isEqualTo(reviewList.size());
    }


    @Test
    @DisplayName("검색 조건이 있을 경우 검색 조건으로 정렬이 되는지 확인")
    public void findAllReviewLowScoreTest() {
        // given
        accommodationsRepository.save(accommodations);
        List<Review> reviewList = ReviewFixture.generateReviewList();
        for (Review review : reviewList) {
            review.setRoom(accommodations.getRooms().get(0));
        }
        reviewRepository.saveAll(reviewList);
        List<Long> list = List.of(accommodations.getRooms().get(0).getRoomSeq());
        ReviewDto.ReqRes reqRes = new ReviewDto.ReqRes();
        reqRes.setFilterType("LOWSCORE");
        // when
        Page<Review> reviews = reviewRepository.findAllReviewByRoomListForPaging(list, PageRequest.of(0, 20),reqRes);
        // then
        Assertions.assertThat(reviews.getContent().get(0).getContent()).isEqualTo("리뷰2");
        Assertions.assertThat(reviews.getContent().size()).isEqualTo(reviewList.size());
    }

    @Test
    @DisplayName("숙소에 대한 리뷰 중, 베스트 리뷰들을 잘 가져오는지 확인")
    public void findAllReviewByBest() {
        // given
        accommodationsRepository.save(accommodations);
        List<Review> reviewList = ReviewFixture.generateReviewList();
        for (Review review : reviewList) {
            review.setRoom(accommodations.getRooms().get(0));
        }
        reviewRepository.saveAll(reviewList);
        List<Long> list = List.of(accommodations.getRooms().get(0).getRoomSeq());
        // when
        List<Review> reviews = reviewRepository.findAllReviewByRoomListToBest(list);
        // then
        List<Review> best = reviewList.stream().filter(Review::isBestYn).collect(Collectors.toList());
        Assertions.assertThat(reviews.size()).isEqualTo(best.size());
    }
}
