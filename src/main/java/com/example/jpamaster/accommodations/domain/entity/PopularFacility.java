package com.example.jpamaster.accommodations.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "popular_facility")
public class PopularFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long popularFacilitySeq;

    @Column(name = "facility_name")
    private String name;

    @Column(name = "facility_logo_url")
    private String logoUrl;

    // TODO: 등록, 수정일지 공통으로 되면 넣기
}
