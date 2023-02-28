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
import com.example.jpamaster.accommodations.repository.room.RoomReposittory;
import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import com.example.jpamaster.popular_facility.repository.PopularFacilityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    List<Room> rooms = new ArrayList<>();
    PopularFacility popularFacility;

    @BeforeEach
    public void setUp() {
        popularFacility = PopularFacilityFixture.generatePopularFacility();
        popularFacilityRepository.save(popularFacility);
        accommodations = Fixture.generateAccommodation(List.of(popularFacility));

        review = ReviewFixture.generateReview();
    }

    @Test
    @DisplayName("리뷰 저장 확인")
    public void addReviewTest() {
        // when
        review.setRoom(rooms.get(0));
        reviewRepository.save(review);

        // then
        Assertions.assertThat(review.getSeq()).isGreaterThan(0L);
    }

    @Test
    @DisplayName("특정 숙소에 해당하는 각 항목별 별점 합산 조회 확인")
    public void findEachPartScoreTest() {
        accommodationsRepository.save(accommodations);
        // given
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




}
