package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accommodation_seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("숙박 판매자 Seq")
    @Column(name = "accommodation_seller_seq")
    private Long seq;

    @Comment("판매자 이름")
    @Column(name = "seller_name")
    private String sellerName;

    @Comment("판매자 아이다")
    @Column(name = "seller_id")
    private String sellerId;

    @Comment("판매자 비밀번호")
    @Column(name = "seller_pwd")
    private String sellerPwd;

    @Comment("판매자 생년월일")
    @Column(name = "seller_birth")
    private String sellerBirth;

    @Comment("판매자 휴대전화")
    @Column(name = "seller_phone_no")
    private String phoneNo;

    @Comment("사업자등록번호")
    @Column(name = "seller_business_no")
    private String businessNo;

    @Comment("사업자 주소")
    @Column(name = "seller_address")
    private String address;

    @Comment("사업자 이메일")
    @Column(name = "seller_email")
    private String email;

    @Comment("판매자 상태")
    @Column(name = "seller_status")
    @Enumerated(value = EnumType.STRING)
    private AccomodationsEnum.SellerStatus status;

    @OneToMany(mappedBy = "seller")
    private List<Accommodations> accommodations = new ArrayList<>();
}
