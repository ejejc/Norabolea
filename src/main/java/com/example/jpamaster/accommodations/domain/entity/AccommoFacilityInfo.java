package com.example.jpamaster.accommodations.domain.entity;

import lombok.*;

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
    @Setter
    private Accommodations accommodation;

    @JoinColumn(name = "popular_facility_seq")
    @ManyToOne
    private PopularFacility popularFacility;

    private Integer sort;
}
