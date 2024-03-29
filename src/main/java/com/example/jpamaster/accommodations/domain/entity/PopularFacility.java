package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "popular_facility")
public class PopularFacility extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "popular_facility_seq")
    private Long popularFacilitySeq;

    @Column(name = "facility_name")
    private String name;

    @Column(name = "facility_logo_url")
    private String logoUrl;
}
