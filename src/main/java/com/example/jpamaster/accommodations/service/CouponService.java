package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Coupon;
import com.example.jpamaster.accommodations.dto.CouponDto;
import com.example.jpamaster.accommodations.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Long addCoupon(CouponDto couponDto) {
        Coupon saveCoupon = couponRepository.save(couponDto.toEntity());
        return saveCoupon.getId();
    }
}
