package com.example.jpamaster.flight.domain;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "airplane")
@Entity
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airplaneSeq;

    @Comment("제조사")
    @Column(name = "code")
    private String code;

    @Comment("제작사")
    @Column(name = "manufacturer")
    private String manufacturer;

    @Comment("형식")
    @Column(name = "type")
    private String type;

    @Comment("표준 좌석수")
    @Column(name = "standard_seat_count")
    private Integer standardSeatCount;

    @Comment("최대 좌석수")
    @Column(name = "max_seat_count")
    private Integer maxSeatCount;

    @Comment("와이파이 사용 가능 여부")
    @Column(name = "wifi_usable")
    private Boolean wifiUsable;

    @Comment("전원 콘센트 사용 가능 여부")
    @Column(name = "power_consent_usable")
    private Boolean powerConsentUsable;

    @Comment("usb 사용 가능 여부")
    @Column(name = "usb_usable")
    private Boolean usbUsable;

    @Comment("비디오 유형")
    @Column(name = "video_type")
    private FlightEnums.VideoType videoType;

    @Comment("기내 음식 유형")
    @Column(name = "food_supply_type")
    private FlightEnums.FoodSupplyType foodSupplyType;


    @Comment("항공사")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "airline_seq")
    private Airline airline;








}
