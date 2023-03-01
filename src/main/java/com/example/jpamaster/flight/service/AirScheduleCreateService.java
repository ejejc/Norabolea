package com.example.jpamaster.flight.service;

import com.example.jpamaster.common.exception.JpaMasterNotFoundException;
import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.airschedule.AirScheduleRepository;
import com.example.jpamaster.flight.web.dto.req.AirScheduleRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirScheduleCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirScheduleCreateService {

    private final SeatService seatService;
    private final FlightValidationService flightValidationService;
    private final AirScheduleTokenBucketService airScheduleTokenBucketService;

    private final AirScheduleRepository airScheduleRepository;

    @Transactional
    public AirScheduleCreateResponseDto createAirSchedule(AirScheduleRequestDto dto) {

        // TODO 비행 스케줄 검증 후 비행 스케줄 등록 필요 - 타이트한 검증 처리
        Airport fromAirport = flightValidationService.airScheduleAirportValidation(dto.getFromAirportSeq());
        Airport toAirport = flightValidationService.airScheduleAirportValidation(dto.getToAirportSeq());
        Airplane airplane = flightValidationService.airScheduleAirplaneValidation(dto.getAirplaneSeq());
        Airline airline = airplane.getAirline();
        flightScheduleValidation(dto, fromAirport, toAirport, airplane);

        // 스케줄 등록
        AirSchedule airSchedule = AirSchedule.createAirSchedule(
            fromAirport, toAirport, airplane, airline,
            dto.getExpectedTakeoffDate(), dto.getExpectedTakeoffTime()
        );

        // 좌석 정보 등록
        seatService.createAirScheduleSeatType(dto.getAirScheduleSeatRequestDtos())
            .forEach(airScheduleSeatType -> airScheduleSeatType.registerAirSchedule(airSchedule));

        airSchedule.mappingAirScheduleReservationBucket(
            airScheduleTokenBucketService.createDefaultFlightTicketTokenBucket(
                airSchedule.getTotalAvailableSeatCount(),
                airline.getAirlineType().getAirlineCostMultipleRate()
            )
        );

        // 저장
        AirSchedule savedAirSchedule = airScheduleRepository.save(airSchedule);

        return new AirScheduleCreateResponseDto(
            savedAirSchedule.getAirScheduleSeq(),
            savedAirSchedule.getDepartAt(),
            savedAirSchedule.getArriveAt(),
            savedAirSchedule.getFlightDistanceKm(),
            savedAirSchedule.getEstimatedHourSpent(),
            savedAirSchedule.getEstimatedMinuteSpent()
        );
    }

    private void flightScheduleValidation(AirScheduleRequestDto dto, Airport fromAirport,
        Airport toAirport, Airplane airplane) {
        flightValidationService.airplaneSeatValidation(airplane.getAirplaneSeatTypes(),
            dto.getAirScheduleSeatRequestDtos());
        flightValidationService.availableAirlineValidation(airplane, fromAirport, toAirport);
        flightValidationService.takeOffTimeValidation(fromAirport.getLocationEn(), dto.getExpectedTakeoffDate(),
            dto.getExpectedTakeoffTime());
    }


    @Transactional
    public AirScheduleCreateResponseDto updateAirSchedule(Long airScheduleSeq, AirScheduleRequestDto dto) {
        AirSchedule airSchedule = airScheduleRepository.findById(airScheduleSeq)
            .orElseThrow(() -> new JpaMasterNotFoundException("해당 스케줄 정보가 존재하지 않습니다."));

        Airport fromAirport = flightValidationService.airScheduleAirportValidation(dto.getFromAirportSeq());
        Airport toAirport = flightValidationService.airScheduleAirportValidation(dto.getToAirportSeq());
        Airplane airplane = airSchedule.getAirplane();
        flightScheduleValidation(dto, fromAirport, toAirport, airplane);

        airSchedule.updateAirSchedule(fromAirport, toAirport, dto.getExpectedTakeoffDate(),
            dto.getExpectedTakeoffTime());

        return new AirScheduleCreateResponseDto(
            airSchedule.getAirScheduleSeq(),
            airSchedule.getDepartAt(),
            airSchedule.getArriveAt(),
            airSchedule.getFlightDistanceKm(),
            airSchedule.getEstimatedHourSpent(),
            airSchedule.getEstimatedMinuteSpent()
        );
    }
}
