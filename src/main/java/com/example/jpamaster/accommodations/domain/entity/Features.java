package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "features")
public class Features extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "features_seq")
    private Long featuresSeq;

    @Column(name = "features_name")
    private String featuresName;

    @Column(name = "features_icon_url")
    private String featuresIconUrl;
}
