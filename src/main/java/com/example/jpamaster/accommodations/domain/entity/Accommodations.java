package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity  // 기본키 설정이 안되어 있으면 ide 에서 컴파일 에러
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accommodations")  // 숙박 업소
public class Accommodations {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accommodations_seq")
    private Long accommodationSeq;

    @Column(name = "accommodations_title")
    @Comment("숙박 업소 명")
    private String accommodationTitle;

    @Column(name = "contact")
    @Comment("숙박 전화번호")
    private String contact;

    // TODO: 값 타입 jpa 학습하기
    @Embedded
    @Comment("배송지 정보")
    private Address address;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "accommodations_type")
    @Comment("숙박 종류")
    private AccomodationsEnum.Type accommodationsType;

    @OneToMany(mappedBy = "accommodations", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "accommodation"/*, cascade = CascadeType.ALL*/ , fetch = FetchType.LAZY)
    private List<AccommoFacilityInfo> accommoFacilityInfos;

    @ManyToOne() // TODO: fetchType 공부해서 넣어주기
    @JoinColumn(name = "accommodation_seller_seq")
    private Seller seller;

    public void addRoom(Room room) {
        // accommodations 입장에서는 연관관계의 주인이 아니기 때문에 룸 객체를 반드시 넣어주지 않아도 된다.
        // 하지만 룸 입장에서는 숙박과의 관계에서 연관관계의 주인이기 때문에 반드시 넣어줘야 한다. - 그래야 맵핑이 된다!!
        // 근데 이를 넣어준 이유는, 숙박 객체 등록 시, 룸 객체를 같이 등록해줘야 하기 때문에 entity 저장 시에도 숙박 객체 안에 room 객체를 넣으면 db에 넣어주기 위함이다.
        // 이를 위해 숙박 엔티티 객체의 rooms에 cascadeType을 준 것이다!
        // 숙박 저장 시, 룸을 같이 저장하지 않아도 된다면 cascade 옵션도 필요 없고, 숙박 entity에 room entity를 굳이 안넣어줘도 되지 않을까..?
        this.rooms.add(room);
        room.setAccommodations(this);
    }

    @Builder
    public Accommodations(String accommodationTitle, String contact, Address address, AccomodationsEnum.Type accommodationsType) {
        this.accommodationTitle = accommodationTitle;
        this.contact = contact;
        this.address = address;
        this.accommodationsType = accommodationsType;
        this.rooms = new ArrayList<>();
    }
}
