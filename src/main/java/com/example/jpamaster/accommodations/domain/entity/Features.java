package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
