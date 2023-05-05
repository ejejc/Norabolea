package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Coupon;
import lombok.Data;

import static com.example.jpamaster.accommodations.enums.OrdersEnum.*;

@Data
public class CouponDto {
    private Long amount;
    private CouponDiscountType type;
    private String name;

    public Coupon toEntity() {
        return Coupon.builder()
                .amount(this.amount)
                .name(this.name)
                .discoutType(type).build();
    }
}
