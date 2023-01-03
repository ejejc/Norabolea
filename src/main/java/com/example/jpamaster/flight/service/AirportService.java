package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
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

    @Transactional(readOnly = true)
    public List<AirportDto> getAirportBySearchCondition(KeywordSearchConditionDto airportSearchConditionDto) {
        List<Airport> top3Airport = airportRepository.findTop3BySearchCondition(airportSearchConditionDto.getKeyword(), PageRequest.of(0, 3));
        /**
         * model mapper 의 속성들을 설정해주지 않는 경우 기본적으로 setter 매핑
         */
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper
//                .getConfiguration()
//                .setDeepCopyEnabled(true)
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
//                .setSkipNullEnabled(true)
//                .setFieldMatchingEnabled(true);


        return top3Airport.stream()
                .map(airport -> modelMapper.map(airport, AirportDto.class))
                .collect(Collectors.toList());
    }
}
