package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "coupon_use")
public class CouponUse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupon_use_seq")
    private Long id;

    @Column(name = "use_yn")
    private Boolean useYn;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @ManyToOne
    @JoinColumn(name = "coupon_seq")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;
}
