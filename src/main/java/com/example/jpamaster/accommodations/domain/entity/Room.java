package com.example.jpamaster.accommodations.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("객실 Seq")
    @Column(name = "room_seq")
    private Long roomSeq;

    @Comment("숙박 Seq")
    private Long accommodationSeq;

    @Comment("객실 가격")
    @Column(name = "room_price")
    private Integer roomPrice;

    @Comment("기준 인원")
    @Column(name = "room_standard_person")
    private int standardPerson;

    @Comment("최대 인원")
    @Column(name = "room_max_person")
    private int maxPerson;

    @Comment("체크인 시간")
    @Column(name = "check_in_time")
    private String checkInTime;

    @Comment("체크아웃 시간")
    @Column(name = "check_out_time")
    private String checkOutTime;

    @Comment("대실 여부")
    @Column(name = "borrow_yn")
    private boolean borrowYn;

    @Comment("사용 여부")
    @Column(name = "use_yn")
    private boolean useYn;
}
