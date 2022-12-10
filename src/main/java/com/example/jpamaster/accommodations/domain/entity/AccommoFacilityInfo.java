package com.example.jpamaster.accommodations.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "accommodation_facility_info")
public class AccommoFacilityInfo {

    @Column(name = "accommo_facility_seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long seq;

    @Column(name = "accommodation_seq")
    private Long accommodationSeq;

    @Column(name = "popular_facility_seq")
    private Long popularFacilitySeq;

    private int sort;
}
