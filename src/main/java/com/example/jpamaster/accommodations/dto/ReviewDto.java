package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.ReviewMedia;
import com.example.jpamaster.accommodations.domain.entity.Room;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDto {

    @Getter
    public static class BestReq {
        private Long reviewSeq;
        private boolean bestYn;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReqRes {
        private Long roomSeq;
        private String content;
        private int kindnessStarScore;
        private int convenienceStarScore;
        private int cleanlinessStarScore;
        private int locationStarScore;
        private String roomName;
        private List<FeaturesDto.Feature> featureList;
        private LocalDateTime regDate;
        private double totalStarScore;
        private boolean bestYn;
        private List<Medias> mediaList;
        private String filterType;
        private Long accommodationSeq;
        private AnswerDto.Res answer;

        public static ReqRes changeToDto(Review review) {
           return ReqRes.builder()
                    .totalStarScore((review.getCleanlinessStarScore()+ review.getConvenienceStarScore()+ review.getKindnessStarScore()+ review.getLocationStarScore()) / 4.0)
                    .content(review.getContent())
                    .roomName(review.getRoom().getRoomName())
                    .mediaList(review.getReviewMedias().stream()
                            .map(Medias::changeToDto)
                            .collect(Collectors.toList()))
                   .featureList(FeaturesDto.Feature.makeFeaturesDto(review.getRoom().getRoomFeaturesInfoList()))
                   .answer(AnswerDto.Res.toDto(review.getAnswer()))
                   .bestYn(review.isBestYn())
                   .regDate(review.getCreatedAt())
                   .build();
        }
        public Review changeToEntity(Room room) {
            Review review =  Review.builder()
                    .room(room)
                    .content(this.content)
                    .kindnessStarScore(this.kindnessStarScore)
                    .convenienceStarScore(this.convenienceStarScore)
                    .locationStarScore(this.locationStarScore)
                    .cleanlinessStarScore(this.cleanlinessStarScore)
                    .build();

            for (Medias vo : mediaList) {
                review.add(vo.changeToEntity());
            }
            return review;
        }

        public ReqRes(int kindnessStarScore, int convenienceStarScore, int cleanlinessStarScore, int locationStarScore) {
            this.kindnessStarScore = kindnessStarScore;
            this.convenienceStarScore = convenienceStarScore;
            this.cleanlinessStarScore = cleanlinessStarScore;
            this.locationStarScore = locationStarScore;
            this.totalStarScore = ( this.kindnessStarScore + this.convenienceStarScore + this.cleanlinessStarScore + this.locationStarScore ) / 4.0;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Medias {
        private String url;
        private boolean useYn;

        public ReviewMedia changeToEntity() {
            return ReviewMedia.builder()
                                                                                                                          .mediaUrl(this.url)
                    .useYn(this.useYn).build();

        }

        public static Medias changeToDto(ReviewMedia reviewMedia) {
            return Medias.builder()
                    .url(reviewMedia.getMediaUrl())
                    .useYn(reviewMedia.isUseYn())
                    .build();
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class ReviewSum {
        private Long roomSeq;
        private int cleanlinessSum;
        private int convenienceSum;
        private int kindnessSum;
        private int locationSum;
        private Long reviewCnt;

        @QueryProjection
        public ReviewSum(Long roomSeq, int cleanlinessSum, int convenienceSum, int kindnessSum, int locationSum, Long reviewCnt) {
            this.roomSeq = roomSeq;
            this.cleanlinessSum = cleanlinessSum;
            this.convenienceSum = convenienceSum;
            this.kindnessSum = kindnessSum;
            this.locationSum = locationSum;
            this.reviewCnt = reviewCnt;
        }
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
