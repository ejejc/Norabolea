package com.example.jpamaster.flight.service;

import com.example.jpamaster.common.enums.HttpStatusCode;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.domain.repository.AvailableAirlineRepository;
import com.example.jpamaster.flight.exception.BadRequestException;
import com.example.jpamaster.flight.exception.NotFounException;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSeatRegisterRequestDto;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlightValidationService {

    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;
    private final AvailableAirlineRepository availableAirlineRepository;

    boolean createAirScheduleValidation (Airplane airplane, Airport fromAirport, Airport toAirport) {
        if (availableAirlineRepository.countByAirline_AirlineSeqAndAirport_AirportSeqIn(airplane.getAirline().getAirlineSeq(), List.of(fromAirport.getAirportSeq(), toAirport.getAirportSeq())) < 2) {
            throw new BadRequestException("취항 하려는 공항 정보가 잘못되었습니다.");
        }

        return true;
    }

     Airport createAirScheduleAirportValidation(Long airportSeq) {
        Optional<Airport> optionalAirport = airportRepository.findByAirportSeq(airportSeq);
        if (optionalAirport.isEmpty()) {
            throw new NotFounException(HttpStatusCode.NOT_FOUND, "존재하지 않는 공항입니다.");
        }
        return optionalAirport.get();
    }

    Airplane createAirScheduleAirplaneValidation(Long airplaneSeq) {
        Optional<Airplane> optionalAirplane = airplaneRepository.findByAirplaneSeqAndAvailableIsTrue(airplaneSeq);

        if (optionalAirplane.isEmpty()) {
            throw new NotFounException(HttpStatusCode.NOT_FOUND, "존재하지 않는 항공기입니다.");
        }

        return optionalAirplane.get();
    }

    void airplaneSeatValidation(List<AirplaneSeatType> airplaneSeatTypes, Set<AirScheduleSeatRegisterRequestDto> airScheduleSeatRegisterRequestDtos) {
        for (AirScheduleSeatRegisterRequestDto dto : airScheduleSeatRegisterRequestDtos) {
            AirplaneSeatType seatType = airplaneSeatTypes.stream()
                .filter(airplaneSeatType -> airplaneSeatType.getSeatType() == dto.getSeatType())
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format("%s - 해당 좌석 타입이 존재하지 않습니다.", dto.getSeatType().getKrName())));

            if (seatType.getAvailableSeatCount() < dto.getAvailableSeatCount()) {
                throw new BadRequestException(
                    String.format("최대 좌석수를 초과했습니다. 최대 좌석수 - %d.", seatType.getAvailableSeatCount()));
            }
        }
    }
}
