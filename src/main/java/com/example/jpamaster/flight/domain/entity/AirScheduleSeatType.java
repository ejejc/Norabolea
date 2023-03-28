package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.flight.enums.FlightEnums;
import com.example.jpamaster.flight.enums.FlightEnums.DisplayType;
import com.example.jpamaster.flight.enums.FlightEnums.FoodType;
import java.util.Objects;
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
public class AirScheduleSeatType extends SeatType {

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

    @Comment("소아 허용 좌석수")
    @Column(name = "available_child_seat_count")
    private Integer availableChildSeatCount;

    @Comment("현재 이용 가능 좌석수")
    @Column(name = "actual_available_seat_count")
    private Integer actualAvailableSeatCount;

    @Comment("현재 이용 가능 소아 좌석 수")
    @Column(name = "actual_available_child_seat_count")
    private Integer actualAvailableChildSeatCount;


    @Builder
    public AirScheduleSeatType(FlightEnums.SeatType seatType, Integer availableSeatCount,
        FoodType foodType, Integer availableBaggageCount, Integer availableBaggageWeight,
        DisplayType displayType, Boolean wifiAvailability, Boolean usbAvailability, Integer availableChildSeatCount) {
        super(seatType, availableSeatCount);
        this.foodType = foodType;
        this.availableBaggageCount = availableBaggageCount;
        this.availableBaggageWeight = availableBaggageWeight;
        this.displayType = displayType;
        this.wifiAvailability = wifiAvailability;
        this.usbAvailability = usbAvailability;

        this.availableChildSeatCount = availableChildSeatCount > availableSeatCount / 10
            ? availableSeatCount / 10
            : availableChildSeatCount;

        this.actualAvailableSeatCount = availableSeatCount;
        this.actualAvailableChildSeatCount = this.availableChildSeatCount;
    }

    public void registerAirSchedule(AirSchedule airSchedule) {
        this.airSchedule = airSchedule;
        airSchedule.mappingToAirScheduleSeatType(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirScheduleSeatType that = (AirScheduleSeatType) o;
        return Objects.equals(airSchedule, that.airSchedule)
            && Objects.equals(super.getSeq(), that.getSeq())
            && Objects.equals(super.getSeatType(), that.getSeatType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getSeq(), super.getSeatType(), airSchedule);
    }

}
