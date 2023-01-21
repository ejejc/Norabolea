package com.example.jpamaster.flight.domain.repository.airschedule;

import static com.example.jpamaster.flight.domain.entity.QAirSchedule.airSchedule;
import static com.example.jpamaster.flight.domain.entity.QAirScheduleSeatType.airScheduleSeatType;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AirScheduleCustomRepositoryImpl implements AirScheduleCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<AirSchedule> findAirScheduleBySearch(AirScheduleSearchRequestDto dto) {
        JPAQuery<AirSchedule> baseQuery = queryFactory
            .from(airSchedule)
            .join(airScheduleSeatType)
            .where(
                airSchedule.deptAirport.airportSeq.eq(dto.getDepartAirportSeq()),
                airSchedule.arrAirport.airportSeq.eq(dto.getArriveAirportSeq()),
                airSchedule.departAt.after(dto.getDepartAt()),
                airScheduleSeatType.availableSeatCount.goe(dto.getTotalPersonCount())
            )
            .distinct()
            .select(airSchedule);

        JPAQuery<Long> countQuery = queryFactory.from(airSchedule)
            .join(airScheduleSeatType)
            .where(
                airSchedule.deptAirport.airportSeq.eq(dto.getDepartAirportSeq()),
                airSchedule.arrAirport.airportSeq.eq(dto.getArriveAirportSeq()),
                airSchedule.departAt.after(dto.getDepartAt()),
                airScheduleSeatType.availableSeatCount.goe(dto.getTotalPersonCount())
            )
            .distinct()
            .select(airSchedule.count());

        return PageableExecutionUtils.getPage(baseQuery.fetch(), Pageable.ofSize(50), countQuery::fetchOne);
    }
}
