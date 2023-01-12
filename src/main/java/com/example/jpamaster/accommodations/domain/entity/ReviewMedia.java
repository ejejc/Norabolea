package com.example.jpamaster.accommodations.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_media")
public class ReviewMedia extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_media_seq")
    private Long seq;

    @Column(name = "review_media_url")
    private String mediaUrl;

    @Column(name = "use_yn")
    private boolean useYn;

    @ManyToOne
    @JoinColumn(name = "review_seq")
    @Setter
    private Review review;
}
