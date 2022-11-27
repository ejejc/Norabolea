package com.example.jpamaster.accommodations.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("객실 Seq")
    @Column(name = "room_id")
    private Long roomSeq;

    @Comment("객실 가격")
    @Column(name = "room_price")
    private Long roomPrice;

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

    @Comment("사용 여부")
    @Column(name = "use_yn")
    private boolean useYn;

    @ManyToOne
    @JoinColumn(name = "accommodations_seq")
    private Accommodations accommodations;

    @OneToMany(mappedBy = "room")
    private List<Media> rooms = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "borrow_room_seq")
    private BorrowRoom borrowRoom;
}
