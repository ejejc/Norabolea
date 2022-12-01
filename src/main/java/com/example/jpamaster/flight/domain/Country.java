package com.example.jpamaster.flight.domain;

import com.example.jpamaster.flight.web.dto.req.CountryInfo;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@EqualsAndHashCode(of = {"countryEngNm", "CountryIsoAlp2", "countryNm", "isoAlp3", "isoNum"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long countrySeq;

    private String countryEngNm;
    private String CountryIsoAlp2;
    private String countryNm;
    private String isoAlp3;
    private String isoNum;

    private Country(String countryEngNm, String countryIsoAlp2, String countryNm, String isoAlp3, String isoNum) {
        this.countryEngNm = countryEngNm;
        this.CountryIsoAlp2 = countryIsoAlp2;
        this.countryNm = countryNm;
        this.isoAlp3 = isoAlp3;
        this.isoNum = isoNum;
    }

    public static Country toEntity(CountryInfo.Country country) {
        return new Country(
                country.getCountryEngNm(),
                country.getCountryIsoAlp2(),
                country.getCountryNm(),
                country.getIsoAlp3(),
                country.getIsoNum()
        );
    }
}
