package com.example.jpamaster.accommodations.repository;

import com.example.jpamaster.accommodations.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
