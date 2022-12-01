package com.example.jpamaster.flight.web.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CountryInfo {

    private int currentCount;

    @JsonProperty(value = "data")
    private List<Country> countries;

    private int numOfRows;
    private int pageNo;
    private int resultCode;
    private String resultMsg;
    private int totalCount;

    @Getter
    public static class Country {
        @JsonProperty("country_eng_nm")
        private String countryEngNm;
        @JsonProperty("country_iso_alp2")
        private String CountryIsoAlp2;
        @JsonProperty("country_nm")
        private String countryNm;
        @JsonProperty("iso_alp3")
        private String isoAlp3;
        @JsonProperty("iso_num")
        private String isoNum;

        @Override
        public String toString() {
            return "Country{" +
                    "countryEngNm='" + countryEngNm + '\'' +
                    ", CountryIsoAlp2='" + CountryIsoAlp2 + '\'' +
                    ", countryNm='" + countryNm + '\'' +
                    ", isoAlp3='" + isoAlp3 + '\'' +
                    ", isoNum='" + isoNum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CountryInfo{" +
                "currentCount=" + currentCount +
                ", countries=" + countries +
                ", numOfRows=" + numOfRows +
                ", pageNo=" + pageNo +
                ", resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }
}
