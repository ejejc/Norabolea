package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import com.example.jpamaster.flight.service.FlightTicketTokenBucketService;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "flight_ticket_token_bucket")
@Getter
@Entity
public class FlightTicketTokenBucket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Enumerated(EnumType.STRING)
    private BucketTokenType bucketTokenType;
    private Integer availableTokenCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_schedule_seq")
    private AirSchedule airSchedule;

    protected FlightTicketTokenBucket() {
    }

    private FlightTicketTokenBucket(Integer totalAvailableSeatCount) {
        this.bucketTokenType = BucketTokenType.DEFAULT;
        this.availableTokenCount = totalAvailableSeatCount / 10;
    }

    public void mappingAirSchedule(AirSchedule airSchedule) {
        this.airSchedule = airSchedule;
    }

    public static FlightTicketTokenBucket createDefault(Integer totalAvailableSeatCount) {
        return new FlightTicketTokenBucket(totalAvailableSeatCount);
    }
}
