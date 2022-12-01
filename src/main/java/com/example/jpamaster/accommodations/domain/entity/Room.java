package com.example.jpamaster.accommodations.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@NoArgsConstructor
@AllArgsConstructor
@Setter
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

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST)
    private List<Media> media;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "borrow_room_seq")
    private BorrowRoom borrowRoom;

    @Builder
    public Room(Long roomPrice, int standardPerson, int maxPerson, String checkInTime, String checkOutTime, boolean useYn, BorrowRoom borrowRoom) {
        this.roomPrice = roomPrice;
        this.standardPerson = standardPerson;
        this.maxPerson = maxPerson;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.useYn = useYn;
        this.media = new ArrayList<>();
        this.borrowRoom = borrowRoom;
    }

    public void add(Media media) { // TODO: 메소드 명 수정
        this.media.add(media);
        media.setRoom(this);
    }
}
