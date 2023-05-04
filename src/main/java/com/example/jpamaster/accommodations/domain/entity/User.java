package com.example.jpamaster.accommodations.domain.entity;


import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;

import java.util.List;

import static com.example.jpamaster.users.enums.UserEnums.*;

@Entity(name = "user")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_seq")
    private Long seq;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_id")
    private String id;

    @Column(name = "user_pwd")
    private String pwd;

    @Column(name = "user_role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<CouponUse> couponUseList;

    @OneToMany(mappedBy = "user")
    private List<Point> pointList;

    @OneToMany(mappedBy = "user")
    private List<Orders> ordersList;
}
