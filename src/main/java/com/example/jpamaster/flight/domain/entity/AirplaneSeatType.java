package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@Table(name = "airplane_seat_type")
@AttributeOverride(
    name = "seq",
    column = @Column(name = "airplane_seat_type_seq")
)
@Entity
public class AirplaneSeatType extends SeatType{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;

    @Builder
    public AirplaneSeatType (FlightEnums.SeatType seatType, Integer availableSeatCount) {
        super(seatType, availableSeatCount);
    }

    public void registerAirplane(Airplane airplane) {
        this.airplane = airplane;
        airplane.getAirplaneSeatTypes().add(this);
    }
}
