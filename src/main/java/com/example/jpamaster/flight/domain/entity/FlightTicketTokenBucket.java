package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
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
import lombok.Getter;

@Table(name = "flight_ticket_token_bucket")
@Getter
@Entity
public class FlightTicketTokenBucket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long flightTicketTokenBucketSeq;

    @Column(name = "bucket_token_type")
    @Enumerated(EnumType.STRING)
    private BucketTokenType bucketTokenType;

    @Column(name = "available_token_count")
    private Integer availableTokenCount = 0;

    @Column(name = "sort_reference")
    private double sortReference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_schedule_seq")
    private AirSchedule airSchedule;

    protected FlightTicketTokenBucket() {
    }

    private FlightTicketTokenBucket(Integer totalAvailableSeatCount, double costMultiple) {
        this.bucketTokenType = BucketTokenType.DEFAULT;
        this.availableTokenCount = totalAvailableSeatCount / 10;
        sortReference = new BigDecimal(this.bucketTokenType.getCostMultiple() * costMultiple)
            .setScale(3, RoundingMode.HALF_UP)
            .doubleValue();
    }

    public void mappingAirSchedule(AirSchedule airSchedule) {
        this.airSchedule = airSchedule;
    }

    public static FlightTicketTokenBucket createDefault(Integer totalAvailableSeatCount, double airlineCostMultiple) {
        return new FlightTicketTokenBucket(totalAvailableSeatCount, airlineCostMultiple);
    }
}
