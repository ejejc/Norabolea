package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirScheduleRepository;
import com.example.jpamaster.flight.util.DistanceUtils;
import com.example.jpamaster.flight.web.dto.req.AirScheduleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirScheduleCreateService {

    private final FlightValidationService flightValidationService;
    private final AirScheduleRepository airScheduleRepository;

    @Transactional
    public void createAirSchedule (AirScheduleCreateRequestDto dto) {

        // TODO 비행 스케줄 검증 후 비행 스케줄 등록 필요 - 타이트한 검증 처리
        Airport fromAirport = flightValidationService.createAirScheduleValidationAirport(dto.getFromAirportSeq());
        Airport toAirport = flightValidationService.createAirScheduleValidationAirport(dto.getToAirportSeq());

        Airplane airplane = flightValidationService.createAirScheduleValidationAirplane(dto.getAirplaneSeq());

        if (flightValidationService.createAirScheduleValidation(airplane, fromAirport, toAirport)) {

            int distance = DistanceUtils.getDistanceAsKm(
                    getDoubleFromStr(fromAirport.getLat()),
                    getDoubleFromStr(fromAirport.getLon()),
                    getDoubleFromStr(toAirport.getLat()),
                    getDoubleFromStr(toAirport.getLon())
            );

            // TODO 평균 비행 속도와 비행 거리에 따른 비행 시간 계산하기
            // TODO 비행 시간에 따른 도착시간 구하기
        }
    }

    private double getDoubleFromStr (String str) {
        return Double.parseDouble(str);
    }

}
