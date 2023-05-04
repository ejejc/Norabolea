package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

import static com.example.jpamaster.accommodations.enums.OrdersEnum.*;

@Entity(name = "order_master")
public class Orders extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "adults")
    private Integer adultNum;

    @Column(name = "childrens")
    private Integer childNum;

    @Column(name = "order_price")
    private Long price;

    @Column(name = "order_status")
    private Status status;

    @Column(name = "order_display_name")
    private String displayName;

    @Column(name = "order_display_phone")
    private String displayPhone;

    @ManyToOne
    @JoinColumn(name = "coupon_use_seq")
    private CouponUse couponUse;

    @ManyToOne
    @JoinColumn(name = "point_seq")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;
}
