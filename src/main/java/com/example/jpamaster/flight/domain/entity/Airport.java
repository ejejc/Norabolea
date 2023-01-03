package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "airport")
@Entity
public class Airport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airportSeq;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_kr")
    private String nameKr;

    @Column(name = "IATA_code")
    private String IATACode;

    @Column(name = "ICAO_code")
    private String ICAOCode;

    @Column(name = "location_kr")
    private String locationKr;

    @Column(name = "location_en")
    private String locationEn;

    @Column(name = "country_en")
    private String countryEn;

    @Column(name = "country_kr")
    private String countryKr;

    @Column(name = "city_en")
    private String cityEn;

    @Column(name = "lat")
    private String lat;
    @Column(name = "lon")
    private String lon;

    @Column(name = "available_airplane_cnt")
    private Integer availableAirplaneCnt;

    @Column(name = "search_count")
    private Long searchCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableAirline> availableAirline;


    @Builder(
            builderMethodName = "airportGenerator",
            buildMethodName = "generate"
    )
    public Airport(String nameEn, String nameKr, String IATACode, String ICAOCode,
                   String locationKr, String locationEn, String countryEn, String countryKr, String cityEn,
                   String lat, String lon) {

        Assert.notNull(nameEn);
        Assert.notNull(nameKr);
        Assert.notNull(IATACode);
        Assert.notNull(ICAOCode);
        Assert.notNull(locationKr);
        Assert.notNull(locationEn);
        Assert.notNull(countryEn);
        Assert.notNull(countryKr);
        Assert.notNull(cityEn);
        Assert.notNull(lat);
        Assert.notNull(lon);

        this.nameEn = nameEn;
        this.nameKr = nameKr;
        this.IATACode = IATACode;
        this.ICAOCode = ICAOCode;
        this.locationKr = locationKr;
        this.locationEn = locationEn;
        this.countryEn = countryEn;
        this.countryKr = countryKr;
        this.cityEn = cityEn;
        this.lat = lat;
        this.lon = lon;
        this.searchCount = 0L;
        this.availableAirplaneCnt = 0;
        this.availableAirline = new ArrayList<>();
    }

    @Override
    public String toString () {
        return "Airport{" +
                "airportSeq=" + airportSeq +
                ", nameEn='" + nameEn + '\'' +
                ", nameKr='" + nameKr + '\'' +
                ", IATACode='" + IATACode + '\'' +
                ", ICAOCode='" + ICAOCode + '\'' +
                ", locationKr='" + locationKr + '\'' +
                ", locationEn='" + locationEn + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", countryKr='" + countryKr + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", availableAirplaneCnt=" + availableAirplaneCnt +
                ", searchCount=" + searchCount +
                '}';
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(nameEn, airport.nameEn) && Objects.equals(nameKr, airport.nameKr) && Objects.equals(IATACode, airport.IATACode) && Objects.equals(ICAOCode, airport.ICAOCode) && Objects.equals(locationKr, airport.locationKr) && Objects.equals(locationEn, airport.locationEn) && Objects.equals(countryEn, airport.countryEn) && Objects.equals(countryKr, airport.countryKr) && Objects.equals(cityEn, airport.cityEn) && Objects.equals(lat, airport.lat) && Objects.equals(lon, airport.lon);
    }

    @Override
    public int hashCode () {
        return Objects.hash(nameEn, nameKr, IATACode, ICAOCode, locationKr, locationEn, countryEn, countryKr, cityEn, lat, lon);
    }
}
