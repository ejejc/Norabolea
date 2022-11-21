package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity  // 기본키 설정이 안되어 있으면 ide 에서 컴파일 에러
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodations")  // 숙박 업소
public class Accommodations {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)  // TODO: generated value 의 4가지 전략에 대해 학습하기
    @Column(name = "accommodations_seq")
    private Long accommodationSeq;

    @Column(name = "accommodations_title")
    @Comment("숙박 업소 명")
    private String accommodationTitle;

    @Column(name = "contact")
    @Comment("숙박 전화번호")
    private String contact;

    @Column(name = "lat")
    @Comment("위도")
    private String lat;

    @Column(name = "lon")
    @Comment("경도")
    private String lon;

    // TODO: 값 타입 jpa 학습하기
    @Embedded
    @Comment("배송지 정보")
    private Address address;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "accommodations_type")
    @Comment("숙박 종류")
    private AccomodationsEnum.Type accommodationsType;

    @OneToMany(mappedBy = "accommodations")
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "accommodation_seller_seq")
    private Seller seller;
    // 기본 숙박 금액
    /*@Column(name = "base_cost")
    @ColumnDefault(value = "0.00")
    private double baseCost;*/

}
