package com.example.jpamaster.flight.service;

import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirScheduleRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.domain.repository.AvailableAirlineRepository;
import com.example.jpamaster.flight.exception.FlightBadRequestException;
import com.example.jpamaster.flight.exception.FlightNotFoundException;
import com.example.jpamaster.flight.web.dto.req.AirScheduleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirScheduleCreateService {

    private final FlightValidationService flightValidationService;
    private final AirScheduleRepository airScheduleRepository;

    @Transactional
    public void createAirSchedule(AirScheduleCreateRequestDto dto) {

        Airport fromAirport = flightValidationService.createAirScheduleValidationAirport(dto.getFromAirportSeq());
        Airport toAirport = flightValidationService.createAirScheduleValidationAirport(dto.getFromAirportSeq());

        Airplane airplane = flightValidationService.createAirScheduleValidationAirplane(dto.getAirplaneSeq());

        if (flightValidationService.createAirScheduleValidationAirline(airplane.getAirline(), List.of(dto.getFromAirportSeq(), dto.getFromAirportSeq()))) {

            AirSchedule airSchedule = AirSchedule.createAirSchedule()
                    .airplane(airplane)
                    .deptAirport(fromAirport)
                    .arrAirport(toAirport)
                    .expectedTakeoffAt(dto.getExpectedTakeoffAt())
                    .expectedLandingAt(dto.getExpectedLandingAt())
                    .create();

            airScheduleRepository.save(airSchedule);
        }




    }


}
