package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.flight.enums.FlightEnums.AirlineType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(of = "airlineSeq", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Where(clause = " deleted = false ")
@Table(name = "airline")
@Entity
public class Airline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airlineSeq;

    @Comment("항공사 이미지")
    @Column(name = "airline_image")
    private String airlineImage;

    @Comment("항공사 이름")
    @Column(name = "airline_name")
    private String airlineName;

    @Comment("항공사 전화 번호")
    @Column(name = "airline_tel")
    private String airlineTel;

    @Comment("항공사 국제 전화 번호")
    @Column(name = "airline_ic_tel")
    private String airlineIcTel;

    @Comment("항공사 IATA 코드")
    @Column(name = "airline_iata")
    private String airlineIata;

    @Comment("항공사 ICAO 코드")
    @Column(name = "airline_iaco")
    private String airlineIcao;

    @Comment("항공사 종류")
    @Column(name = "airline_type")
    @Enumerated(EnumType.STRING)
    private AirlineType airlineType;

    @Comment("삭제 여부")
    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableAirline> availableAirline;


    @Builder
    public Airline(String airlineImage, String airlineName, String airlineTel, String airlineIcTel, String airlineIata, String airlineIcao) {
        this.airlineImage = airlineImage;
        this.airlineName = airlineName;
        this.airlineTel = airlineTel;
        this.airlineIcTel = airlineIcTel;
        this.airlineIata = airlineIata;
        this.airlineIcao = airlineIcao;
        this.deleted = false;
        this.availableAirline = new ArrayList<>();
        this.airlineType = AirlineType.randomType();
    }

    public void updateAirlineInfo(String airlineName, String airlineTel, String airlineIcTel) {
        this.airlineName = airlineName;
        this.airlineTel = airlineTel;
        this.airlineIcTel = airlineIcTel;
    }
}
