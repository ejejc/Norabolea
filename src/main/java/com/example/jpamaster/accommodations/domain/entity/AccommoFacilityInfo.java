package com.example.jpamaster.accommodations.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "accommodation_facility_info")
public class AccommoFacilityInfo {

    @Column(name = "accommo_facility_seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long seq;

    @JoinColumn(name = "accommodations_seq")
    @ManyToOne
    private Accommodations accommodation;

    @JoinColumn(name = "popular_facility_seq")
    @ManyToOne
    private PopularFacility popularFacility;

    private int sort;
}
