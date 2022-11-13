package com.example.jpamaster.accommodations.domain;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity  // 기본키 설정이 안되어 있으면 ide 에서 컴파일 에러
@Table(name = "accommodations")  // 숙박 업소
public class Accommodations {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)  // TODO: generated value 의 4가지 전략에 대해 학습하기
    @Column(name = "accommodations_seq")
    private Long accommodationSeq;

    @Column(name = "accommodations_name")  // 숙박 업소 명
    private String accommodationName;

    @Column(name = "lat")  // 위도  // TODO: 컬럼의 기본 설명 추가하는 법 찾아보기
    private String lat;

    @Column(name = "lon")  // 경도
    private String lon;

    // 주소지 - Address  // TODO: 값 타입 jpa 학습하기
    @Embedded
    private Address address;

    @Column(name = "contact")  // 연결 전화번호
    private String contact;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "accommodations_type") // 숙박 종류 - enum 호텔 / 모텔 / 등
    private AccommodationsType accommodationsType;

    // 기본 숙박 금액
    @Column(name = "base_cost")
    @ColumnDefault(value = "0.00")
    private double baseCost;


}
