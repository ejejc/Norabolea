package com.example.jpamaster.flight.web;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jpamaster.flight.fixture.Fixture;
import com.example.jpamaster.flight.service.AirlineService;
import com.example.jpamaster.flight.web.dto.req.AirlineUpdateRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirlineDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(
    controllers = AirlineController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class}
)
@AutoConfigureMockMvc
class AirlineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirlineService airlineService;

    @DisplayName("항공사 리스트 반환 요청 테스트")
    @Test
    void givenAirlineSearchCondition_whenRequestGetAirlineList_thenReturnPagedAirlineDto() throws Exception {
        // given
        AirlineDto airlineDtoFixture = Fixture.generateAirlineDto();

        BDDMockito.given(airlineService.getAirlineListByCondition(any(), any()))
            .willReturn(new PageImpl<>(List.of(airlineDtoFixture)));

        // when
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/admin/airline")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data.content[0].airlineName").value(airlineDtoFixture.getAirlineName())
            )
            .andReturn();

        // then
        BDDMockito
            .verify(airlineService, BDDMockito.times(1))
            .getAirlineListByCondition(any(), any());

        Assertions.assertThat(mvcResult.getResponse().getContentAsString()).isNotBlank();
    }

    @DisplayName("항공사 업데이트 요청 테스트")
    @Test
    void givenAirlineUpdateRequestDto_whenUpdateAirlineInfo_thenReturnUpdatedAirlineSeq() throws Exception {
        // given
        long airlineSeq = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        AirlineUpdateRequestDto airlineUpdateRequestDto = new AirlineUpdateRequestDto(
            "airlineName", "airlineTel", "airlineIcTel"
        );

        BDDMockito.given(airlineService.updateAirlineInfo(any(), any(), any()))
            .willReturn(airlineSeq);

        // when
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/admin/airline/{airlineSeq}", airlineSeq)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(airlineUpdateRequestDto)))
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.data").value(airlineSeq)
            )
            .andReturn();

        // then
        BDDMockito
            .verify(airlineService, BDDMockito.times(1))
            .updateAirlineInfo(any(), any(), any());

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);

    }
}