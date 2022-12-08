package com.example.jpamaster.accommodations.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ApiModel(value = "주소")
public class Address {

    @ApiModelProperty(value = "주소")
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ApiModelProperty(value = "상세 주소")
    @Column(name = "delivery_address_detail")
    private String deliveyAddressDetail;

    @ApiModelProperty(value = "우편 번호")
    @Column(name = "delivery_post_no")
    private String deliveryPostNo;
}
