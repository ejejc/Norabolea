package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.flight.enums.FlightEnums;
import com.example.jpamaster.flight.enums.FlightEnums.DisplayType;
import com.example.jpamaster.flight.enums.FlightEnums.FoodType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "air_schedule_seat_type")
@AttributeOverride(
    name = "seq",
    column = @Column(name = "air_schedule_seat_type_seq")
)
@Entity
public class AirScheduleSeatType extends SeatType{

    @Comment("항공 스케줄 시퀀스")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_schedule_seq")
    private AirSchedule airSchedule;

    @Comment("기내식 종류")
    @Column(name = "food_type")
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @Comment("이용 가능 수하물 수")
    @Column(name = "available_baggage_count")
    private Integer availableBaggageCount;

    @Comment("이용 가능 수하물 무게 per unit")
    @Column(name = "available_baggage_weight")
    private Integer availableBaggageWeight;

    @Comment("디스플레이 종류")
    @Column(name = "display_type")
    @Enumerated(EnumType.STRING)
    private DisplayType displayType;

    @Comment("와이파이 사용 가능 여부")
    @Column(name = "wifi_availability")
    private Boolean wifiAvailability;

    @Comment("usb 사용 가능 여부")
    @Column(name = "usb_availability")
    private Boolean usbAvailability;

    @Builder
    public AirScheduleSeatType(FlightEnums.SeatType seatType, Integer availableSeatCount) {
        super(seatType, availableSeatCount);
    }

    public void registerAirSchedule(AirSchedule airSchedule) {
        this.airSchedule = airSchedule;
        airSchedule.getAirScheduleSeatTypes().add(this);
    }
}
