package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservation_bucket_token_type")
@Entity
public class ReservationBucketTokenType {

    @Id
    @Enumerated(EnumType.STRING)
    private BucketTokenType bucketTokenType;

    @ColumnDefault(value = "1.0")
    private double reservationBucketCostMultipleRate;

    @Builder
    public ReservationBucketTokenType(BucketTokenType bucketTokenType, double reservationBucketCostMultipleRate) {
        this.bucketTokenType = bucketTokenType;
        this.reservationBucketCostMultipleRate = reservationBucketCostMultipleRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReservationBucketTokenType that = (ReservationBucketTokenType) o;
        return bucketTokenType == that.bucketTokenType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucketTokenType);
    }

    @Override
    public String toString() {
        return "ReservationBucketTokenType{" +
            "bucketTokenType=" + bucketTokenType +
            ", multipleCostRate=" + reservationBucketCostMultipleRate +
            '}';
    }
}
