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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
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
        popularFacility = PopularFacility.builder()
                .popularFacilitySeq(1L)
                .name("주차가능")
                .logoUrl("/media/park").build();
        accommodations = Fixture.generateAccommodation(List.of(popularFacility));
    }
    @Test
//    @ParameterizedTest
//    @ValueSource
    @DisplayName("숙소 리뷰들의 평균 별점 및 총 개수를 검증한다.")
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

    @Test
    @DisplayName("숙소 조회 후, dto로 변환이 잘 되었는지 검증한다.")
    public void findAccommodationTest() {
        // given
        when(accommodationsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(accommodations));
        // when
        AccommodationDto dto = accommodationService.findAccommodation(anyLong());
        // then
        Assertions.assertThat(dto.getRooms()).isNotEmpty();
        Assertions.assertThat(dto.getRooms().get(0).getMediaList()).isNotEmpty();
        Assertions.assertThat(dto.getRooms().get(0).getRoomFeaturesInfoList()).isNotEmpty();
        Assertions.assertThat(dto.getFacilityInfoRes()).hasSize(accommodations.getAccommoFacilityInfos().size());
    }


}
