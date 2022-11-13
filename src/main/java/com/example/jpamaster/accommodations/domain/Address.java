package com.example.jpamaster.accommodations.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "zip_code")
    private String zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "base_address")
    private String baseAddress;

    @Column(name = "detail_address")
    private String detailAddress;
}
