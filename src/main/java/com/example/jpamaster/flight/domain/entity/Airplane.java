package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "airplane")
@Where(clause = " available = true ")
@Entity
public class Airplane extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airplaneSeq;

    @Comment("제작사")
    @Column(name = "manufacturer")
    private String manufacturer;

    @Comment("비행기 코드")
    @Column(name = "code")
    private String code;

    @Comment("형식")
    @Column(name = "type")
    private String type;

    @Comment("항공사")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_seq")
    private Airline airline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_airport_seq")
    private Airport currentAirport;

    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AirplaneSeatType> airplaneSeatTypes = new HashSet<>();

    @Column(name = "available")
    private Boolean available;

    @Builder
    public Airplane (String manufacturer, String code, String type, Airline airline, Airport currentAirport) {
        this.manufacturer = manufacturer;
        this.code = code;
        this.type = type;
        this.airline = airline;
        this.currentAirport = currentAirport;
        this.available = true;
        this.airplaneSeatTypes = new HashSet<>();
    }
}
