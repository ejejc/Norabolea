package com.example.jpamaster.accommodations.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Column(name = "review_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "content")
    private String content;

    @Column(name = "kindness_star_score")
    private int kindnessStarScore;

    @Column(name = "cleanliness_star_score")
    private int cleanlinessStarScore;

    @Column(name = "convenience_star_score")
    private int convenienceStarScore;

    @Column(name = "location_star_score")
    private int locationStarScore;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "review")
    private List<ReviewMedia> reviewMedias;

    // private User user; TODO: 추후 추가 예정
    // TODO: 등록, 수정일지 공통으로 되면 넣기
}
