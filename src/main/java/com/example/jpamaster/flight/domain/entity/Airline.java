package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    public void updateAirlineInfo(String airlineName, String airlineTel, String airlineIcTel) {
        this.airlineName = airlineName;
        this.airlineTel = airlineTel;
        this.airlineIcTel = airlineIcTel;
    }
}
