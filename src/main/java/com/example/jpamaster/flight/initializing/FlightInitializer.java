package com.example.jpamaster.flight.initializing;

import com.example.jpamaster.flight.constant.FlightConstant;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.feign.AirlineFeignClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class FlightInitializer {
    @Value("${open-api.airline.api-key}")
    private String serviceKey;

    private final AirlineFeignClient airlineFeignClient;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;


    @Transactional
    @EventListener(value = ApplicationReadyEvent.class)
    public void airlineInitializeEvent () {
        AirlineInfoVo airlineInfo = airlineFeignClient.fetchAirline(serviceKey, "json");
        if (airlineInfo != null) {
            List<Airline> airlines = new ArrayList<>();

            for (AirlineInfoVo.Response.Body.Item item : airlineInfo.getResponse().getBody().getItems()) {

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

                    airlines.add(airline);
                } else {
                    Airline airline = optionalAirline.get();
                    airline.updateAirlineInfo(item.getAirlineName(), item.getAirlineTel(), item.getAirlineIcTel());
                }
            }
            airlineRepository.saveAll(airlines);
        }
    }

    @Transactional
    @EventListener(value = ApplicationReadyEvent.class)
    public void airportInitializeEvent () {
        String filePath = "static/airport/airport_info.csv";
        ClassPathResource classPathResource = new ClassPathResource(filePath);

        if(!classPathResource.exists()){
            log.error("Invalid filePath : {}", filePath);
        } else {
            log.info("file path exists = {}", classPathResource.exists());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8))) {
                List<Airport> airportList = new ArrayList<>();

                while (true) {
                    String line = br.readLine();

                    if (!StringUtils.hasText(line)) break;

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

                        airportList.add(airport);
                    }
                }

                airportRepository.saveAll(airportList);

            } catch (IOException e) {
                log.error("[AIRPORT] parseToAirport >> file stream has got an error", e);
            }
        }
    }

    @Getter
    public static class AirlineInfoVo {

        private AirlineInfoVo.Response response;

        @Getter
        static class Response {
            private Object header;
            private AirlineInfoVo.Response.Body body;

            @Getter
            static class Body {
                private List<Item> items;

                @Getter
                static class Item {
                    private String airlineImage;
                    private String airlineName;
                    private String airlineTel;
                    private String airlineIcTel;
                    private String airlineIata;
                    private String airlineIcao;
                }
            }
        }
    }
}
