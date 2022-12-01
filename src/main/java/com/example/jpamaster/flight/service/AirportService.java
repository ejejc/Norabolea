package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.Airport;
import com.example.jpamaster.flight.domain.AirportRepository;
import com.example.jpamaster.flight.web.dto.req.AirportSearchCondition;
import com.example.jpamaster.flight.web.dto.res.AirportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Transactional
    @PostConstruct
    public void saveAirportByFile() {
        airportRepository.deleteAllInBatch();

        String filePath = "static/airport/airport_20211231.csv";
        ClassPathResource classPathResource = new ClassPathResource(filePath);

        if(!classPathResource.exists()){
            log.error("Invalid filePath : {}", filePath);
        } else {
            log.info("file path exists = {}", classPathResource.exists());
            try (BufferedReader br = new BufferedReader(new InputStreamReader(classPathResource.getInputStream(), Charset.forName("euc-kr")))) {
                List<Airport> airportList = new ArrayList<>();

                while (true) {
                    String line = br.readLine();

                    if (!StringUtils.hasText(line)) break;
                    else if (line.startsWith("-")) continue;

                    String[] airportInfo = line.split(",");


                    /**
                     * airportInfo[0] - 영문공항명
                     * airportInfo[1] - 한글공항명
                     * airportInfo[2] - 공항코드1(IATA)
                     * airportInfo[3] - 공항코드2(ICAO)
                     * airportInfo[4] - 지역
                     * airportInfo[5] - 영문국가명
                     * airportInfo[6] - 한글국가명
                     * airportInfo[7] - 영문도시명
                     */


                    Airport airport = Airport.airportGenerator()
                            .nameEn(airportInfo[0])
                            .nameKr(airportInfo[1])
                            .IATACode(airportInfo[2])
                            .ICAOCode(airportInfo[3])
                            .location(airportInfo[4])
                            .countryEn(airportInfo[5])
                            .countryKr(airportInfo[6])
                            .cityEn(airportInfo[7])
                            .generate();

                    airportList.add(airport);
                }


                airportRepository.saveAll(airportList);

            } catch (IOException e) {
                log.error("[AIRPORT] parseToAirport >> file stream has got an error", e);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<AirportDto> getAirportBySearchCondition(AirportSearchCondition airportSearchCondition) {
        List<Airport> top3Airport = airportRepository.findTop3BySearchCondition(airportSearchCondition.getKeyword(), PageRequest.of(0, 3));
        /**
         * model mapper 의 속성들을 설정해주지 않는 경우
         */
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setDeepCopyEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true);

        return top3Airport.stream()
                .map(airport -> modelMapper.map(airport, AirportDto.class))
                .collect(Collectors.toList());
    }
}
