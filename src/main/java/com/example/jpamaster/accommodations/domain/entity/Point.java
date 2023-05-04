package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.enums.OrdersEnum;
import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;

@Entity(name = "point")
public class Point extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_seq")
    private Long id;

    @Column(name = "point_amount")
    private Long amount;

    @Column(name = "point_type")
    private OrdersEnum.PointType type;

    @Column(name = "point_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;
}
