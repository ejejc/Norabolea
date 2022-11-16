package com.example.jpamaster.accommodations.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_address_detail")
    private String deliveyAddressDetail;

    @Column(name = "delivery_post_no")
    private String deliveryPostNo;
}
