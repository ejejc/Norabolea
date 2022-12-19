package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
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

    @Column(name = "location")
    private String location;

    @Column(name = "country_en")
    private String countryEn;

    @Column(name = "country_kr")
    private String countryKr;

    @Column(name = "city_en")
    private String cityEn;

    @Column(name = "search_count")
    private Long searchCount;


    @Builder(
            builderMethodName = "airportGenerator",
            buildMethodName = "generate"
    )
    public Airport(String nameEn, String nameKr, String IATACode, String ICAOCode,
                   String location, String countryEn, String countryKr, String cityEn) {

        Assert.notNull(nameEn);
        Assert.notNull(nameKr);
        Assert.notNull(IATACode);
        Assert.notNull(ICAOCode);
        Assert.notNull(location);
        Assert.notNull(countryEn);
        Assert.notNull(countryKr);
        Assert.notNull(cityEn);

        this.nameEn = nameEn;
        this.nameKr = nameKr;
        this.IATACode = IATACode;
        this.ICAOCode = ICAOCode;
        this.location = location;
        this.countryEn = countryEn;
        this.countryKr = countryKr;
        this.cityEn = cityEn;
        this.searchCount = 0L;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airportSeq=" + airportSeq +
                ", nameEn='" + nameEn + '\'' +
                ", nameKr='" + nameKr + '\'' +
                ", IATACode='" + IATACode + '\'' +
                ", ICAOCode='" + ICAOCode + '\'' +
                ", location='" + location + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", countryKr='" + countryKr + '\'' +
                ", cityEn='" + cityEn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(nameEn, airport.nameEn)
                && Objects.equals(nameKr, airport.nameKr)
                && Objects.equals(IATACode, airport.IATACode)
                && Objects.equals(ICAOCode, airport.ICAOCode)
                && Objects.equals(location, airport.location)
                && Objects.equals(countryEn, airport.countryEn)
                && Objects.equals(countryKr, airport.countryKr)
                && Objects.equals(cityEn, airport.cityEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEn, nameKr, IATACode, ICAOCode, location, countryEn, countryKr, cityEn);
    }
}
