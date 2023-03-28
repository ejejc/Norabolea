package com.example.jpamaster.flight.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.AirScheduleSeatType;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.entity.AirScheduleReservationBucket;
import com.example.jpamaster.flight.domain.repository.airschedule.AirScheduleRepository;
import com.example.jpamaster.flight.fixture.Fixture;
import com.example.jpamaster.flight.web.dto.req.AirScheduleRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirScheduleCreateResponseDto;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AirScheduleCreateServiceTest {

    @InjectMocks
    private AirScheduleCreateService airScheduleCreateService;

    @Mock
    private FlightValidationService flightValidationService;

    @Mock
    private SeatService seatService;

    @Mock
    private AirScheduleTokenBucketService airScheduleTokenBucketService;

    @Mock
    private AirScheduleRepository airScheduleRepository;


    private Airport fromAirport;
    private Airport toAirport;
    private Airplane airplane;
    private Set<AirScheduleSeatType> airScheduleSeatTypeSet;
    private AirSchedule airSchedule;
    private AirScheduleReservationBucket airScheduleReservationBucket;
    private Airline airline;

    private final String takeOffDate = "20230101";
    private final String takeOffTime = "1230";

    @BeforeEach
    public void setup() {
        List<Airport> airports = Fixture.generateAllAirportByFixture();
        fromAirport = airports.get(0);
        toAirport = airports.get(airports.size() - 1);
        airplane = Fixture.generateAirplane();
        airScheduleSeatTypeSet = Fixture.generateAirScheduleSeatTypeSet();
        airline = airplane.getAirline();
        airSchedule = AirSchedule.createAirSchedule(fromAirport, toAirport, airplane, airline,
            takeOffDate, takeOffTime);
        airScheduleReservationBucket = Fixture.generateFlightTicketTokenBucket();
    }

    @DisplayName("항공 스케줄을 등록한다.")
    @Test
    void givenAirScheduleRequestDto_whenCreateAirSchedule_thenCreateAirSchedule() {
        // given
        AirScheduleRequestDto dto = new AirScheduleRequestDto(
            1L, 1L, 1L, takeOffDate, takeOffTime);

        stubbingForAirSchedule();

        // when
        AirScheduleCreateResponseDto airScheduleResponse = airScheduleCreateService.createAirSchedule(dto);

        // then
        BDDMockito.then(airScheduleRepository).should().save(any());
        BDDMockito.verify(airScheduleRepository, Mockito.times(1)).save(any());
        Assertions.assertAll(
            () -> assertNotNull(airScheduleResponse),
            () -> org.assertj.core.api.Assertions.assertThat(airScheduleResponse.getArriveAt())
                .isEqualTo(airSchedule.getArriveAt()),
            () -> org.assertj.core.api.Assertions.assertThat(airScheduleResponse.getDepartAt())
                .isEqualTo(airSchedule.getDepartAt()),
            () -> org.assertj.core.api.Assertions.assertThat(airScheduleResponse.getEstimatedHourSpent())
                .isEqualTo(airSchedule.getEstimatedHourSpent()),
            () -> org.assertj.core.api.Assertions.assertThat(airScheduleResponse.getEstimatedMinuteSpent())
                .isEqualTo(airSchedule.getEstimatedMinuteSpent()),
            () -> org.assertj.core.api.Assertions.assertThat(airScheduleResponse.getFlightDistanceKm())
                .isEqualTo(airSchedule.getFlightDistanceKm()));

    }

    private void stubbingForAirSchedule() {
        BDDMockito.given(flightValidationService.airScheduleAirportValidation(anyLong())).willReturn(fromAirport, toAirport);
        BDDMockito.given(flightValidationService.airScheduleAirplaneValidation(anyLong())).willReturn(airplane);

        BDDMockito.willDoNothing().given(flightValidationService).airplaneSeatValidation(anySet(), any());
        BDDMockito.willDoNothing().given(flightValidationService).availableAirlineValidation(any(), any(), any());
        BDDMockito.willDoNothing().given(flightValidationService).takeOffTimeValidation(anyString(), anyString(), anyString());
        BDDMockito.given(seatService.createAirScheduleSeatType(ArgumentMatchers.anySet())).willReturn(airScheduleSeatTypeSet);

        BDDMockito.given(airScheduleTokenBucketService.createDefaultFlightTicketTokenBucket(anyInt(), anyDouble())).willReturn(
            airScheduleReservationBucket);
        BDDMockito.given(airScheduleRepository.save(any())).willReturn(airSchedule);
    }
}