package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.web.dto.req.AirplaneRegisterRequestDto;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirplaneService {

    private final SeatService seatService;

    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;

    // TODO 중복 비행기 등록 막기 - 근데 중복의 조건은 뭐지?
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

            Set<AirplaneSeatType> airplaneSeatTypes = seatService.createAirplaneSeatType(dto.getAirplaneSeatRegisterRequestDtos());
            airplaneSeatTypes.forEach(airplaneSeatType -> airplaneSeatType.registerAirplane(airplane));

            airplaneRepository.save(airplane);
        }
    }
}
