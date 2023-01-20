package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "room_features_info")
public class RoomFeaturesInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_features_info_seq")
    private Long roomFeaturesInfoSeq;

    @ManyToOne
    @JoinColumn(name = "features_seq")
    private Features features;

    @Column(name = "sort_num")
    private int sort;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @Setter
    private Room room;
}
