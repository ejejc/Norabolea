package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.web.dto.req.AirlineUpdateRequestDto;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AirlineService {

    private final AirlineRepository airlineRepository;

    @Transactional
    public void deleteAirline(Long airlineSeq) {
        airlineRepository.updateToDeletedByAirlineSeq(airlineSeq);
    }

    @Transactional
    public Long updateAirlineInfo(Long airlineSeq, AirlineUpdateRequestDto dto, MultipartFile airlineImage) {

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
}