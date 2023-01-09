package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.constant.FlightConstant;
import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirScheduleRepository;
import com.example.jpamaster.flight.util.FlightUtils;
import com.example.jpamaster.flight.web.dto.req.AirScheduleCreateRequestDto;
import java.time.LocalDateTime;
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
    private final AirScheduleRepository airScheduleRepository;

    @Transactional
    public void createAirSchedule (AirScheduleCreateRequestDto dto) {

        // TODO 비행 스케줄 검증 후 비행 스케줄 등록 필요 - 타이트한 검증 처리
        Airport fromAirport = flightValidationService.createAirScheduleValidationAirport(dto.getFromAirportSeq());
        Airport toAirport = flightValidationService.createAirScheduleValidationAirport(dto.getToAirportSeq());

        Airplane airplane = flightValidationService.createAirScheduleValidationAirplane(dto.getAirplaneSeq());

        if (flightValidationService.createAirScheduleValidation(airplane, fromAirport, toAirport)) {

            LocalDateTime depart = FlightUtils.toLocalDateTime(dto.getExpectedTakeoffDate(), dto.getExpectedTakeoffTime());

            int distance = FlightUtils.getDistanceAsKm(
                    getDoubleFromStr(fromAirport.getLat()),
                    getDoubleFromStr(fromAirport.getLon()),
                    getDoubleFromStr(toAirport.getLat()),
                    getDoubleFromStr(toAirport.getLon())
            );

            double approximateTime = FlightUtils.round(distance / FlightConstant.AVERAGE_AIRPLANE_SPEED, 2);

            int hour = (int) approximateTime;
            int min = (int) (((approximateTime - hour) * 3600) / 60);

            LocalDateTime arrive = FlightUtils.calculateArriveLocalDateTime(toAirport.getLocationEn(), depart, hour, min);

            AirSchedule airSchedule = AirSchedule.builder()
                    .departAt(depart)
                    .arriveAt(arrive)
                    .estimatedHourSpent(hour)
                    .estimatedMinuteSpent(min)
                    .flightDistanceKm(distance)
                    .airplane(airplane)
                    .deptAirport(fromAirport)
                    .arrAirport(toAirport)
                    .build();

            seatService.registerSeatForAirSchedule(dto.getAirScheduleSeatRegisterRequestDtos(), airSchedule);

            airScheduleRepository.save(airSchedule);
        }
    }

    private double getDoubleFromStr (String str) {
        return Double.parseDouble(str);
    }

}
