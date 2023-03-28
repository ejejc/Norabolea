package com.example.jpamaster.flight.initializing;

import com.example.jpamaster.flight.constant.FlightConstant;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.initializing.FlightInitializer.AirlineInfoVo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FlightInitBusiness {

    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void airlineInitializing(AirlineInfoVo.Response.Body.Item item) {
        Optional<Airline> optionalAirline = airlineRepository.findByAirlineIataAndAirlineIcao(item.getAirlineIata(), item.getAirlineIcao());

        if (optionalAirline.isEmpty()) {
            Airline airline = Airline.builder()
                .airlineImage(item.getAirlineImage())
                .airlineName(item.getAirlineName())
                .airlineTel(item.getAirlineTel())
                .airlineIcTel(item.getAirlineIcTel())
                .airlineIata(item.getAirlineIata())
                .airlineIcao(item.getAirlineIcao())
                .build();

            airlineRepository.save(airline);
        } else {
            Airline airline = optionalAirline.get();
            airline.updateAirlineInfo(item.getAirlineName(), item.getAirlineTel(), item.getAirlineIcTel());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void airportInitializing(String line) {

        String[] airportInfo = line.split(",");

        /**
         * airportInfo[0] - 영문공항명
         * airportInfo[1] - 한글공항명
         * airportInfo[2] - 공항코드1(IATA)
         * airportInfo[3] - 공항코드2(ICAO)
         * airportInfo[4] - 한글 지역
         * airportInfo[5] - 영문 지역
         * airportInfo[6] - 영문국가명
         * airportInfo[7] - 한글국가명
         * airportInfo[8] - 영문도시명
         * airportInfo[9] - 위도
         * airportInfo[10] - 경도
         */

        Optional<Airport> optionalAirport = airportRepository.findByIATACodeAndICAOCode(airportInfo[2], airportInfo[3]);
        if (optionalAirport.isEmpty()) {
            Airport airport = Airport.airportGenerator()
                .nameEn(airportInfo[FlightConstant.ENG_AIRPORT_NAME_IDX])
                .nameKr(airportInfo[FlightConstant.KOR_AIRPORT_NAME_IDX])
                .IATACode(airportInfo[FlightConstant.IATA_CODE_IDX])
                .ICAOCode(airportInfo[FlightConstant.ICAO_CODE_IDX])
                .locationKr(airportInfo[FlightConstant.KOR_AIRPORT_LOCATION_IDX])
                .locationEn(airportInfo[FlightConstant.ENG_AIRPORT_LOCATION_IDX])
                .countryEn(airportInfo[FlightConstant.ENG_COUNTRY_IDX])
                .countryKr(airportInfo[FlightConstant.KOR_COUNTRY_IDX])
                .cityEn(airportInfo[FlightConstant.ENG_CITY_IDX])
                .lat(airportInfo[FlightConstant.LATITUDE_IDX])
                .lon(airportInfo[FlightConstant.LONGITUDE_IDX])
                .generate();

            airportRepository.save(airport);
        }
    }
}
