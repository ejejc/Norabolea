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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "air_schedule_reservation_bucket")
@Getter
@Entity
public class AirScheduleReservationBucket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airScheduleReservationBucketSeq;

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

    private AirScheduleReservationBucket(Integer totalAvailableSeatCount, double airlineCostMultipleRate) {
        this.bucketTokenType = BucketTokenType.DEFAULT;
        this.availableTokenCount = totalAvailableSeatCount / 10;
        sortReference = new BigDecimal(this.bucketTokenType.getDefaultCostMultiple() * airlineCostMultipleRate)
            .setScale(3, RoundingMode.HALF_UP)
            .doubleValue();
    }

    public void mappingAirSchedule(AirSchedule airSchedule) {
        this.airSchedule = airSchedule;
    }

    public static AirScheduleReservationBucket createDefault(
        Integer totalAvailableSeatCount, double airlineCostMultipleRate) {
        return new AirScheduleReservationBucket(totalAvailableSeatCount, airlineCostMultipleRate);
    }
}
