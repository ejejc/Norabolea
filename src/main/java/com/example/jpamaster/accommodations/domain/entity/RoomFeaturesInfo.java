package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "room_features_info")
public class RoomFeaturesInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_features_info_seq")
    private Long roomFeaturesInfoSeq;

    @ManyToOne
    @JoinColumn(name = "features_seq")
    private Features features;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
