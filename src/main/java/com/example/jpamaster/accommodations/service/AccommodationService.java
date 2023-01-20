package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.*;
import com.example.jpamaster.accommodations.dto.*;
import com.example.jpamaster.accommodations.feign.KakaoFeignClient;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.AcommoFacilityInfoRepository;
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
    private final AcommoFacilityInfoRepository acommoFacilityInfoRepository;
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
        // 숙소 저장
        accommodationsRepository.save(accommodations);
        this.saveFaclityInfo(param, accommodations); // TODO: 메소드 todo-list 해결하면 다시 리팩토링 param 전체를 넘기는 것은 아닌 것 같다 ..
    }

    /**
     * 인기 시설 저장
     * @param param
     * @param accommodations
     */
    private void saveFaclityInfo(AccommodationDto param, Accommodations accommodations) {
        // 파라미터로 받은 Seq를 통해 인기시설 엔티티 조회
        List<PopularFacility> popularFacilityList
                = popularFacilityService.searchFacilityEntityToLongs(param.getFacilityInfoReq());
        // 인기시설 엔티티를 통해, 숙소와 인기시설의 중간테이블 저장
        List<AccommoFacilityInfo> collect = popularFacilityList.stream()
                .map(vo -> AccommoFacilityInfo.builder()
                        .accommodation(accommodations)
                        .popularFacility(vo)
                        // TODO: 아래는 dto에서 변환 위에 특징들은 service에서 변환 어떤게 좋을까 ?
                        // TODO: 어떻게 보면 인기시설에 관한건데 숙박 엔티티에다가 넣는 것은 아닌것 같다 ..
                        .sort(param.findSortMatchForSeq(vo.getPopularFacilitySeq()))
                        .build()).collect(Collectors.toList());
        acommoFacilityInfoRepository.saveAll(collect);
    }

    /**
     * dto로 받음 room을 entity로 변환하여 숙소 entity에 셋팅
     * @param rooms
     * @param accommodations
     * @return
     */
    private Accommodations setRoomToAccommodation(List<RoomDto> rooms, Accommodations accommodations) {
        for (RoomDto dto : rooms) {
            Room room = roomService.searchRoomEntity(dto);
            // 특징 seq들을 통해 특징 entity 가져오기
            List<Features> featuresList = featuresService.searchFeaturesListToByIds(dto.getFeatureList());

            for (Features features : featuresList) {
                FeaturesDto.FeatureInfoDto featuresInfoDto
                        = featuresService.searchSameFeatureSeqToEntitySeq(dto.getFeatureList(), features.getFeaturesSeq());
                room.addFeaturesInfo( RoomFeaturesInfo.builder()
                        .features(features)
                        .sort(featuresInfoDto.getSort())
                        .room(room).build());
            }
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
        List<Review> totalReviewList = null;
        if (Objects.nonNull(dto.getRooms())) {
            totalReviewList = new ArrayList<>();
            for (RoomDto roomDto : dto.getRooms()) {
                totalReviewList.addAll(reviewRepository.findAllReviewByRoomSeq(roomDto.getSeq()));
            }
        }
        List<ReviewDto.ReqRes> totalReviewDtoList = null;
        if (Objects.nonNull(totalReviewList)) {
            totalReviewDtoList = totalReviewList.stream()
                    .map(vo -> new ReviewDto.ReqRes(vo.getCleanlinessStarScore(), vo.getKindnessStarScore()
                            , vo.getLocationStarScore(),vo.getConvenienceStarScore()))
                    .collect(Collectors.toList());
        }

        if (!CollectionUtils.isEmpty(totalReviewDtoList)) {
            dto.setTotalReviewCnt(totalReviewList.size());
            double score = totalReviewDtoList.stream()
                    .mapToDouble(ReviewDto.ReqRes::getTotalStarScore).sum();
            dto.setAvgStarScore(score / dto.getTotalReviewCnt());
        }
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
