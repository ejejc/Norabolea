package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.*;
import com.example.jpamaster.accommodations.dto.*;
import com.example.jpamaster.accommodations.feign.KakaoFeignClient;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.common.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AccommodationService {
    private final AccommodationsRepository accommodationsRepository;
    private final ReviewRepository reviewRepository;
    private final KakaoFeignClient kakaoFeignClient;
    private final RoomService roomService;
    private final FeaturesService featuresService;
    private final PopularFacilityService popularFacilityService;
    /**
     * 숙소 추가
     * @param param
     */
    public void addAccommodation(AccommodationDto param) {
        Accommodations accommodations = Accommodations.builder()
                .accommodationTitle(param.getAccommodationTitle())
                .contact(param.getContact())
                .address(param.getAddress())
                .accommodationsType(param.getAccommodationsType())
                .build();
        // dto room을 entity로 변환 후, accommodation에 저장
        this.setRoomToAccommodation(param.getRooms(), accommodations);
        // 인기시설과 숙소 맵핑
        this.saveFaclityInfo(param.getFacilityInfoReq(), accommodations);
        // 숙소 저장
        accommodationsRepository.save(accommodations);
    }

    /**
     * 인기 시설 저장
     * @param facilityReq
     * @param accommodations
     */
    private void saveFaclityInfo(List<AccommoFacilityInfoDto.Req> facilityReq, Accommodations accommodations) {
        // 파라미터로 받은 Seq를 통해 인기시설 엔티티 조회
        List<PopularFacility> popularFacilityList
                = popularFacilityService.searchFacilityEntityToLongs(facilityReq);
        // 인기시설 엔티티를 통해, 숙소와 인기시설의 중간테이블 저장
        for (PopularFacility entity : popularFacilityList) {
            AccommoFacilityInfoDto.Req dto = facilityReq.stream()
                    .filter(vo -> vo.getFacilitySeq().equals(entity.getPopularFacilitySeq())).findFirst().orElseThrow(() -> new InvalidParameterException("유효하지 않은 인기시설 입니다."));
            accommodations.addFacilityInfo(AccommoFacilityInfo.builder()
                    .popularFacility(entity)
                    .sort(dto.getSort()).build());
        }
    }

    /**
     * dto로 받음 room을 entity로 변환하여 숙소 entity에 셋팅
     * @param rooms
     * @param accommodations
     * @return
     */
    private Accommodations setRoomToAccommodation(List<RoomDto> rooms, Accommodations accommodations) {
        for (RoomDto dto : rooms) {
            // room DTO를 Entity로 변환
            Room room = roomService.searchRoomEntity(dto);
            // 특징 seq들을 통해 특징 entity 가져오기
            List<Features> featuresList = featuresService.searchFeaturesListToByIds(dto.getFeatureList());

            for (Features features : featuresList) {
                // 특징 seq가 포함되어 있는 특징 seq dto list를 확인하여 해당 seq에 정해진 sort 정보를 셋팅해준다.
                FeaturesDto.FeatureInfoDto featuresInfoDto
                        = featuresService.searchSameFeatureSeqToEntitySeq(dto.getFeatureList(), features.getFeaturesSeq());
                room.addFeaturesInfo( RoomFeaturesInfo.builder()
                        .features(features)
                        .sort(featuresInfoDto.getSort())
                        .room(room).build());
            }
            // 룸 정보를 숙소 entity에 넣어준다.
            accommodations.addRoom(room);
        }
        return accommodations;
    }

    /**
     * 숙박 정보 조회
     * @param accommodationSeq
     * @return
     */
    public AccommodationDto findAccommodation(Long accommodationSeq) {
        // TODO: 유효성 체크 로직 추가
        Accommodations entity = accommodationsRepository.findById(accommodationSeq).orElseThrow(() -> new InvalidParameterException("유효하지 않은 숙박업체 입니다."));
        AccommodationDto dto = AccommodationDto.changeToDto(entity);
        this.setReviewCntAndReviewScore(dto);
        return dto;
    }

    /**
     * 숙소 평균 리뷰 점수 및 리뷰 개수 구하기
     * // TODO: 리팩토링하기
     * @param dto
     */
    private void setReviewCntAndReviewScore(AccommodationDto dto) {
        List<Long> roomSeqs = dto.getRooms().stream().map(RoomDto::getSeq).collect(Collectors.toList());
        List<ReviewDto.ReviewSum> reviewSums = reviewRepository.findAvgEachScore(roomSeqs);

        for (ReviewDto.ReviewSum info : reviewSums) {
            dto.setAvgStarScore(dto.getAvgStarScore()+info.getEachAvgSum());
        }
        dto.setTotalReviewCnt(reviewSums.size());
        dto.setAvgStarScore(dto.getAvgStarScore() / reviewSums.size());
    }

    /**
     * 숙박 위치 정보 구하기
     * @param query
     * @return
     */
    public KakaoDto.Res findLocationToAccommodation(String query) {
        KakaoDto.Res res = null;
        Map result = kakaoFeignClient.searchLocation(query);
        ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) result.get("documents");
        if (!CollectionUtils.isEmpty(list)) {
             res = KakaoDto.Res.KakaoResOfDto(list.get(0));
        }
        return res;
    }
}
