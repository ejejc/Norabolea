package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.fixture.Fixture;
import com.example.jpamaster.flight.web.dto.req.AirlineUpdateRequestDto;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {

    @InjectMocks
    private AirlineService airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    private Airline airline;

    @BeforeEach
    public void setup() {
        airline = Fixture.generateAirline();
    }

    @DisplayName("항공사를 삭제하면 항공사 상태가 삭제로 바뀐다.")
    @Test
    void given_whenDeleteAirline_thenDeleteAirline() {
        // when
        airlineService.deleteAirline(airline.getAirlineSeq());

        // then
        BDDMockito.verify(airlineRepository, BDDMockito.times(1))
            .updateToDeletedByAirlineSeq(airline.getAirlineSeq());
    }

    @DisplayName("항공사 정보를 수정하면 항공사 정보가 수정된다.")
    @Test
    void givenUpdateAirlineDto_whenUpdateAirlineInfo_thenUpdateAirline() {
        // given
        Long airlineSeq = 1L;

        AirlineUpdateRequestDto dto = new AirlineUpdateRequestDto(
            "new airline name",
            "010-1111-1111",
            "010-2222-2222"
        );
        MockMultipartFile mockMultipart = new MockMultipartFile("mockMultipart", "mockMultipart".getBytes());

        BDDMockito
            .given(airlineRepository.findById(ArgumentMatchers.anyLong()))
            .willReturn(Optional.of(airline));

        // when
        airlineService.updateAirlineInfo(airlineSeq, dto, mockMultipart);

        // then
        Assertions.assertAll(
            () -> org.assertj.core.api.Assertions.assertThat(airline.getAirlineName()).isEqualTo(dto.getAirlineName()),
            () -> org.assertj.core.api.Assertions.assertThat(airline.getAirlineTel()).isEqualTo(dto.getAirlineTel()),
            () -> org.assertj.core.api.Assertions.assertThat(airline.getAirlineIcTel())
                .isEqualTo(dto.getAirlineIcTel()));
    }

    @DisplayName("조건에 맞는 항공사를 검색해서 가져온다.")
    @Test
    void givenConditionSearch_whenGetAirlineList_thenGetAirlinePage() {
        // given

        KeywordSearchConditionDto dto = new KeywordSearchConditionDto();

        Page<Airline> pageResult = new PageImpl<>(Collections.singletonList(airline));

        BDDMockito
            .given(airlineRepository.findAllByKeyword(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Pageable.class)))
            .willReturn(pageResult);

        // when
        Page<AirlineDto> result = airlineService.getAirlineListByCondition(dto, PageRequest.of(0, 50));

        // then
        BDDMockito
            .verify(airlineRepository, BDDMockito.times(1))
            .findAllByKeyword(ArgumentMatchers.anyString(), ArgumentMatchers.any(Pageable.class));

        org.assertj.core.api.Assertions.assertThat(result).hasSize(1);
    }
}