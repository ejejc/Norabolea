package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.ReviewMedia;
import com.example.jpamaster.accommodations.domain.entity.Room;
import lombok.*;

import java.util.List;

public class ReviewDto {

    @Getter
    public static class Req {
        private Long roomSeq;
        private String content;
        private int kindnessStarScore;
        private int convenienceStarScore;
        private int cleanlinessStarScore;
        private int locationStarScore;

        private double totalStarScore;
        private List<Media> mediaList;

        public Review changeToEntity(Room room) {
            Review review =  Review.builder()
                    .room(room)
                    .content(this.content)
                    .kindnessStarScore(this.kindnessStarScore)
                    .convenienceStarScore(this.convenienceStarScore)
                    .locationStarScore(this.locationStarScore)
                    .cleanlinessStarScore(this.cleanlinessStarScore)
                    .build();

            for (Media vo : mediaList) {
                review.add(vo.changeToEntity());
            }
            return review;
        }

        @Builder
        public Req(int kindnessStarScore, int convenienceStarScore, int cleanlinessStarScore, int locationStarScore) {
            this.kindnessStarScore = kindnessStarScore;
            this.convenienceStarScore = convenienceStarScore;
            this.cleanlinessStarScore = cleanlinessStarScore;
            this.locationStarScore = locationStarScore;
            this.totalStarScore = ( this.kindnessStarScore + this.convenienceStarScore + this.cleanlinessStarScore + this.locationStarScore ) / 4.0;
        }
    }

    @Getter
    public static class Media {
        private String url;
        private boolean useYn;

        public ReviewMedia changeToEntity() {
            return ReviewMedia.builder()
                    .mediaUrl(this.url)
                    .useYn(this.useYn).build();

        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class ReviewSum {
        private Long roomSeq;
        private int cleanlinessSum;
        private int convenienceSum;
        private int kindnessSum;
        private int locationSum;
        private Long reviewCnt;
    }

    @Getter
    public static class ReviewSummary {
        private double cleanlinessAvg;
        private double convenienceAvg;
        private double kindnessAvg;
        private double locationAvg;
        private Long reveiwCntSum = 0L;

        public void sum(ReviewSum reviewSum) {
            this.cleanlinessAvg = this.cleanlinessAvg + reviewSum.getCleanlinessSum();
            this.convenienceAvg = this.convenienceAvg + reviewSum.getConvenienceSum();
            this.kindnessAvg = this.kindnessAvg + reviewSum.getKindnessSum();
            this.locationAvg = this.locationAvg + reviewSum.getLocationSum();
            this.reveiwCntSum = this.reveiwCntSum + reviewSum.getReviewCnt();
        }

        public void avg () {
            this.cleanlinessAvg = this.cleanlinessAvg / this.reveiwCntSum;
            this.convenienceAvg = this.convenienceAvg / this.reveiwCntSum;
            this.kindnessAvg = this.kindnessAvg / this.reveiwCntSum;
            this.locationAvg = this.locationAvg / this.reveiwCntSum;
        }
    }
}
