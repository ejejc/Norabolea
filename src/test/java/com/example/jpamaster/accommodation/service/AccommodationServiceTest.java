package com.example.jpamaster.accommodation.service;

import com.example.jpamaster.accommodation.repository.Fixture;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.dto.AccommodationDto;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.example.jpamaster.accommodations.dto.RoomDto;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.service.AccommodationService;
import com.example.jpamaster.accommodations.service.FeaturesService;
import com.example.jpamaster.accommodations.service.PopularFacilityService;
import com.example.jpamaster.accommodations.service.RoomService;
import com.example.jpamaster.popular_facility.repository.PopularFacilityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {
    private PopularFacility popularFacility;
    private Accommodations accommodations;

    @InjectMocks
    private AccommodationService accommodationService;
    @Mock
    private AccommodationsRepository accommodationsRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private RoomService roomService;
    @Mock
    private FeaturesService featuresService;
    @Mock
    private PopularFacilityService popularFacilityService;

    @BeforeEach
    public void setup() {

        accommodations = Fixture.generateAccommodation(List.of(popularFacility));
    }
    @Test
//    @ParameterizedTest
//    @ValueSource
    public void setReviewCntAndReviewScoreTest() {
        // given
        ReviewDto.ReviewSum firstReview = new ReviewDto.ReviewSum(1,2,3,4);
        ReviewDto.ReviewSum secondReview = new ReviewDto.ReviewSum(2,2,3,4);
        List<ReviewDto.ReviewSum> list = Arrays.asList(firstReview, secondReview);

        AccommodationDto dto = new AccommodationDto();
        RoomDto roomDto = new RoomDto();
        roomDto.setSeq(1L);
        dto.setRooms(Collections.singletonList(roomDto));

        when(reviewRepository.findAvgEachScore(anyList())).thenReturn(list);

        // when
        accommodationService.setReviewCntAndReviewScore(dto);

        // then
        Assertions.assertThat(dto.getTotalReviewCnt()).isEqualTo(list.size());
        Assertions.assertThat(dto.getAvgStarScore()).isEqualTo(2.625);
    }


}
