package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.AccommoFacilityInfo;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.dto.AccommoFacilityInfoDto;
import com.example.jpamaster.accommodations.dto.AccommodationDto;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.AcommoFacilityInfoRepository;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import com.example.jpamaster.common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationsRepository accommodationsRepository;
    private final PopularFacilityRepository popularFacilityRepository;
    private final AcommoFacilityInfoRepository acommoFacilityInfoRepository;

    /**
     * 숙소 추가
     * @param param
     */
    public void addAccommodation(AccommodationDto param) {
        Accommodations accommodations = param.changeToEntity();
        accommodationsRepository.save(accommodations);
        // 파라미터로 받은 Seq를 통해 인기시설 엔티티 조회
        List<PopularFacility> popularFacilityList
                = popularFacilityRepository.findAllById(
                param.getFacilityInfoReq().stream()
                        .map(AccommoFacilityInfoDto.Req::getFacilitySeq)
                        .collect(Collectors.toList()));
        // 인기시설 엔티티를 통해, 숙소와 인기시설의 중간테이블 저장
        List<AccommoFacilityInfo> collect = popularFacilityList.stream()
                .map(vo -> AccommoFacilityInfo.builder()
                        .accommodation(accommodations)
                        .popularFacility(vo)
                        .sort(param.findSortMatchForSeq(vo.getPopularFacilitySeq()))
                        .build()).collect(Collectors.toList());
        acommoFacilityInfoRepository.saveAll(collect);

        /*
        List<PopularFacility> popularFacilityList
                = popularFacilityRepository.findAllById(
                        param.getPopularFacilitySeqs().stream()
                                .map(AccommoFacilityInfoDto::getFacilitySeq)
                                .collect(Collectors.toList()));

        // 중간 테이블
        List<AccommoFacilityInfo> collect = popularFacilityList.stream()
                .map(vo -> AccommoFacilityInfo.builder()
                            .accommodation(accommodations)
                            .popularFacility(vo)
                            .sort(param.findSortMatchForSeq(vo.getPopularFacilitySeq()))
                            .build()).collect(Collectors.toList());
        -- 숙소 엔티티에 accommoFacilityInfo 정보를 넣어주지 않아도 조회 시, 문제가 없다.
        accommodations.setAccommoFacilityInfos(collect);
        accommodationsRepository.save(accommodations);*/
    }

    public AccommodationDto findAccommodation(Long accommodationSeq) {
        // TODO: 유효성 체크 로직 추가
        Accommodations entity = accommodationsRepository.findById(accommodationSeq).orElse(null);
        AccommodationDto dto = AccommodationDto.changeToDto(entity);
        return dto;
    }
}
