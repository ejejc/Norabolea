package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.repository.airschedule.AirScheduleRepository;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirScheduleSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AriScheduleService {

    private static final int DEFAULT_JET_OIL_PRICE = 1000;

    private final AirScheduleRepository airScheduleRepository;

    /**
     * 사용자가 검색한 조건에 맞는 낮은 가격 순의 비행 데이터를 보여준다.
     * 1. 가격 책정 정책
     * - 거리, 유류비, 인원수, 좌석 등급, 토큰 버킷 타입, 항공사 종류로 결정
     * - ((거리 * 유류비) * 인원 타입 보정값) * 좌석 등급 보정값 * 토큰 버킷 타입 보정값 * 항공사 종류 보정값
     * - 위의 가격 책정 정책을 인원 수 만큼 반복하여 가격을 책정한다.
     * - 가격 책정 정책에 따라 가격이 책정된 비행 스케줄 데이터를 낮은 가격 순으로 정렬하여 보여준다.
     *
     * 2. 검색 시 정렬 조건
     * - 현재는 일단 직항만 고려
     * - 검색 조건에 따라 변하지 않는 부분 -> 거리, 유류비, 인원수, 좌석 등급, 인원 타입
     * - 검색 조건에 따라 변하는 부분 -> 토큰 버킷 타입, 항공사 종류
     * - 토큰 버킷 타입 보정값과 항공사 종류 보정값을 곱해서 낮은 순서대로 정렬
     * - 1차적으로 낮은 가격의 비행 스케줄을 가지고올 수 있음.
     */


    @Transactional(readOnly = true)
    public Slice<AirScheduleSearchResponseDto> searchAirScheduleByCondition(
        AirScheduleSearchRequestDto dto) {

        Slice<AirScheduleSearchResponseDto> airSchedules = airScheduleRepository.findAirScheduleBySearch(dto);
        airSchedules.forEach(airSchedule -> {
            double basePrice = calculateBasePriceForSchedule(airSchedule);
            int finalPrice = calculateFinalPrice(basePrice, dto.getAdultCount(), dto.getChildCount());

            airSchedule.setPriceForSchedule(finalPrice);
        });

        return airSchedules;
    }

    private int calculateFinalPrice(double basePrice, Integer adultCount, Integer childCount) {
        return (int)(basePrice * adultCount + basePrice * childCount * 0.70);
    }

    private double calculateBasePriceForSchedule(AirScheduleSearchResponseDto airSchedule) {
        return airSchedule.getFlightDistanceKm() * DEFAULT_JET_OIL_PRICE
            * airSchedule.getSeatType().getCostMultiple()
            * airSchedule.getReservationBucketCostMultipleRate()
            * airSchedule.getAirlineType().getAirlineCostMultipleRate();
    }

}
