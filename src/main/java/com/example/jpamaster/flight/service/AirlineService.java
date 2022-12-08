package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.web.dto.req.AirlineRequestDto;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AirlineService {

    @Value("${open-api.airline.request-uri}")
    private String requestUri;

    @Value("${open-api.airline.api-key}")
    private String apiKey;

    private final AirlineRepository airlineRepository;

    @Transactional
    public void deleteAirline(Long airlineSeq) {
        airlineRepository.updateToDeletedByAirlineSeq(airlineSeq);
    }

    @Transactional
    public Long updateAirlineInfo(Long airlineSeq, AirlineRequestDto dto, MultipartFile airlineImage) {

        Optional<Airline> optionalAirline = airlineRepository.findById(airlineSeq);

        if (optionalAirline.isPresent()) {
            // TODO storage 에 파일 저장 후 url 저장
            Airline airline = optionalAirline.get();

            airline.updateAirlineInfo(dto.getAirlineName(), dto.getAirlineTel(), dto.getAirlineIcTel());
        }
        return airlineSeq;
    }

    public Page<AirlineDto> getAirlineListByCondition(KeywordSearchConditionDto searchCondition, Pageable pageable) {
        return airlineRepository.findAllByKeyword(searchCondition.getKeyword(), pageable)
                .map(airline -> new AirlineDto(airline.getAirlineSeq(), airline.getAirlineImage(), airline.getAirlineName(), airline.getAirlineTel(), airline.getAirlineIcTel(), airline.getAirlineIata(), airline.getAirlineIcao()));
    }


    @PostConstruct
    @Transactional
    public void saveAirlineByDefault() {
        String uri = String.format("%s?" +
                        "serviceKey=%s" +
                        "&type=json"
                , requestUri, apiKey);

        try {
            ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);
            if (response.hasBody()) {
                AirlineInfoVo airlineInfoVo = new ObjectMapper().readValue(response.getBody(), AirlineInfoVo.class);

                List<Airline> airlines = new ArrayList<>();

                for (AirlineInfoVo.Response.Body.Item item : airlineInfoVo.getResponse().getBody().getItems()) {

                    if (!airlineRepository.existsByAirlineIataAndAirlineIcao(item.getAirlineIata(), item.getAirlineIcao())) {
                        Airline airline = Airline.builder()
                                .airlineImage(item.getAirlineImage())
                                .airlineName(item.getAirlineName())
                                .airlineTel(item.getAirlineTel())
                                .airlineIcTel(item.getAirlineIcTel())
                                .airlineIata(item.getAirlineIata())
                                .airlineIcao(item.getAirlineIcao())
                                .build();

                        airlines.add(airline);
                    }
                }
                airlineRepository.saveAll(airlines);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}