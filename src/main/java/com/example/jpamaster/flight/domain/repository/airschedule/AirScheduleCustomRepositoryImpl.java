package com.example.jpamaster.flight.domain.repository.airschedule;

import static com.example.jpamaster.flight.domain.entity.QAirSchedule.airSchedule;
import static com.example.jpamaster.flight.domain.entity.QAirScheduleSeatType.airScheduleSeatType;
import static com.example.jpamaster.flight.domain.entity.QAirline.airline;
import static com.example.jpamaster.flight.domain.entity.QAirplane.airplane;

import com.example.jpamaster.flight.domain.entity.QAirport;
import com.example.jpamaster.flight.web.dto.res.AirScheduleSearchResponseDto;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AirScheduleCustomRepositoryImpl implements AirScheduleCustomRepository {

    public static final int DEFAULT_PAGE_SIZE = 50;
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<AirScheduleSearchResponseDto> findAirScheduleBySearch(AirScheduleSearchRequestDto dto) {
        QAirport departAirport = QAirport.airport;
        QAirport arriveAirport = QAirport.airport;

        JPAQuery<?> baseQuery = queryFactory
            .from(airSchedule)
            .join(airScheduleSeatType)
            .on(airScheduleSeatType.seatType.eq(dto.getSeatType()))
            .join(departAirport)
            .on(departAirport.airportSeq.eq(dto.getDepartAirportSeq()))
            .join(arriveAirport)
            .on(arriveAirport.airportSeq.eq(dto.getArriveAirportSeq()))
            .join(airSchedule.airplane, airplane)
            .join(airplane.airline, airline)
            .where(
                departAtAfter(dto.getDepartAt()),
                actualTotalSeatGreaterEquals(dto.getTotalPersonCount()),
                actualChildSeatGreaterEquals(dto.getChildCount()),
                isDirectFlightExpression(dto.isDirectFlight()),
                isFreeBaggageExpression(dto.isFreeBaggage())
            )
            .distinct();

        JPAQuery<AirScheduleSearchResponseDto> listQuery = baseQuery
            .select(
                Projections.fields(
                    AirScheduleSearchResponseDto.class,
                    airSchedule.airScheduleSeq,
                    airSchedule.departAt,
                    airSchedule.arriveAt,
                    airSchedule.flightDistanceKm,
                    airSchedule.estimatedHourSpent,
                    airSchedule.estimatedMinuteSpent,

                    airScheduleSeatType.seq.as("airScheduleSeatTypeSeq"),
                    airScheduleSeatType.seatType,
                    airScheduleSeatType.foodType,
                    airScheduleSeatType.availableBaggageCount,
                    airScheduleSeatType.availableBaggageWeight,
                    airScheduleSeatType.displayType,
                    airScheduleSeatType.wifiAvailability,
                    airScheduleSeatType.usbAvailability,

                    airplane.manufacturer,
                    airplane.code,
                    airplane.type,

                    airline.airlineImage,
                    airline.airlineName,
                    airline.airlineIata,

                    departAirport.nameEn.as("deptNameEn"),
                    departAirport.nameKr.as("deptNameKr"),
                    departAirport.IATACode.as("deptIataCode"),
                    departAirport.countryEn.as("deptCountryEn"),
                    departAirport.countryKr.as("deptCountryKr"),
                    departAirport.cityEn.as("deptCityEn"),

                    arriveAirport.nameEn.as("arrvNameEn"),
                    arriveAirport.nameKr.as("arrvNameKr"),
                    arriveAirport.IATACode.as("arrvIataCode"),
                    arriveAirport.countryEn.as("arrvCountryEn"),
                    arriveAirport.countryKr.as("arrvCountryKr"),
                    arriveAirport.cityEn.as("arrvCityEn")
                )
            );

        JPAQuery<Long> countQuery = baseQuery
            .select(airSchedule.airScheduleSeq.count());

        return PageableExecutionUtils.getPage(
            listQuery.fetch(), PageRequest.of(dto.getPage(), DEFAULT_PAGE_SIZE), countQuery::fetchOne
        );
    }

    private BooleanExpression departAtAfter(LocalDateTime departAt) {
        return departAt == null
            ? null
            : airSchedule.departAt.after(departAt);
    }

    private BooleanExpression actualTotalSeatGreaterEquals(Integer totalPersonCount) {
        return totalPersonCount == null
            ? null
            : airScheduleSeatType.actualAvailableSeatCount.gt(0)
                    .and(airScheduleSeatType.actualAvailableSeatCount.goe(totalPersonCount));
    }

    private BooleanExpression actualChildSeatGreaterEquals(Integer childCount) {
        return childCount == null
            ? null
            : airScheduleSeatType.actualAvailableChildSeatCount.gt(0)
                    .and(airScheduleSeatType.actualAvailableChildSeatCount.goe(childCount));
    }

    // TODO 일단 직항 구현 > 추후 경유 추가
    private BooleanExpression isDirectFlightExpression(boolean directFlight) {
        return null;
    }

    private BooleanExpression isFreeBaggageExpression(boolean freeBaggage) {
        return freeBaggage
            ? airScheduleSeatType.availableBaggageCount.gt(0)
            : null;
    }
}
