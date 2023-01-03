package com.example.jpamaster.accommodations.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BorrowRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrow_room_seq")
    @Comment("대실 seq")
    private Long borrowSeq;

    @Column(name = "borrow_time")
    @Comment("대실 기준 시간")
    private int borrowTime;

    @Column(name = "borrow_price")
    @Comment("대실 기준 가격")
    private long borrowPrice;

    @Column(name = "operate_time")
    @Comment("운영 시간")
    private String operateTime;
}
