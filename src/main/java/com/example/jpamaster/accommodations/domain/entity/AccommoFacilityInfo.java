package com.example.jpamaster.accommodations.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accommodation_facility_info")
@Getter
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

    private Integer sort;
}
