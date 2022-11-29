package com.example.jpamaster.accommodations.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Address {
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_address_detail")
    private String deliveyAddressDetail;

    @Column(name = "delivery_post_no")
    private String deliveryPostNo;
}
