package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.entity.AvailableSeatType;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.web.dto.req.AirplaneRegisterRequestDto;
import com.example.jpamaster.flight.web.dto.req.SeatRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirplaneService {

    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;

    // TODO 중복 비행기 등록 막기
    @Transactional
    public void registerAirplane(Long airlineSeq, AirplaneRegisterRequestDto dto) {
        Optional<Airline> optionalAirline = airlineRepository.findById(airlineSeq);
        Optional<Airport> optionalAirport = airportRepository.findByAirportSeq(dto.getInitialAirportSeq());

        if (optionalAirport.isPresent() && optionalAirline.isPresent()) {
            Airline airline = optionalAirline.get();
            Airport airport = optionalAirport.get();

            Airplane airplane = Airplane.builder()
                    .manufacturer(dto.getManufacturer())
                    .code(dto.getCode())
                    .type(dto.getType())
                    .airline(airline)
                    .currentAirport(airport)
                    .build();

            if (!dto.getSeatRegisterRequestDtos().isEmpty()) {
                for (SeatRegisterRequestDto seatDto : dto.getSeatRegisterRequestDtos()) {
                    AvailableSeatType availableSeatType = AvailableSeatType.builder()
                            .seatType(seatDto.getSeatType())
                            .availableSeatCount(seatDto.getAvailableSeatCount())
                            .build();

                    availableSeatType.registerAirplane(airplane);
                }
            }

            airplaneRepository.save(airplane);
        }
    }
}
