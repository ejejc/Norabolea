package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.jpamaster.accommodations.enums.OrdersEnum.*;

@Entity(name = "coupon")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_seq")
    private Long id;

    @Column(name = "coupon_amount")
    private Long amount;

    @Column(name = "coupon_discount_type")
    private CouponDiscountType discoutType;

    @Column(name = "coupon_name")
    private String name;
}
