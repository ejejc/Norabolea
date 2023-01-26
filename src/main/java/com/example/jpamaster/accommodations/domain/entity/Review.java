package com.example.jpamaster.accommodations.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {

    @Column(name = "review_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "content")
    private String content;

    @Column(name = "best_yn")
    private boolean bestYn;

    @Column(name = "kindness_star_score")
    private int kindnessStarScore;

    @Column(name = "cleanliness_star_score")
    private int cleanlinessStarScore;

    @Column(name = "convenience_star_score")
    private int convenienceStarScore;

    @Column(name = "location_star_score")
    private int locationStarScore;

    @Column(name = "avg_star_score")
    private double avgStartScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "review" ,cascade = CascadeType.PERSIST)
    //@Builder.Default TODO: 이거 사용할때 @Builder에 컴파일 오류 발생 이유 확인해보기
    private List<ReviewMedia> reviewMedias;

    @OneToOne(mappedBy = "review")
    private Answer answer;

    public void add(ReviewMedia mediaEntity) {
        reviewMedias.add(mediaEntity);
        mediaEntity.setReview(this);
    }

    @Builder
    public Review(String content, int kindnessStarScore, int cleanlinessStarScore
            , int convenienceStarScore, int locationStarScore, Room room) {
        this.content = content;
        this.kindnessStarScore = kindnessStarScore;
        this.cleanlinessStarScore = cleanlinessStarScore;
        this.convenienceStarScore = convenienceStarScore;
        this.locationStarScore = locationStarScore;
        this.room = room;
        this.reviewMedias = new ArrayList<>();
        this.avgStartScore = this.calculateAvgStartScore();
    }

    private double calculateAvgStartScore() {
        return (this.kindnessStarScore + this.cleanlinessStarScore + this.convenienceStarScore + this.locationStarScore) / 4.0;
    }

    public void updateBestYn(boolean bestYn) {
        this.bestYn = bestYn;
    }
    // private User user; TODO: 추후 추가 예정
}
