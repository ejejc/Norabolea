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

    @AllArgsConstructor
    @Getter
    public enum FilterType {
        LOWSCORE("lowScore", "별점낮은순"),
        HIGHSCORE("highScore", "별점높은순"),
        RECENTDATE("recentDate", "최근등록순");

        private final String name;
        private final String desc;
    }

    @Getter
    public static class BestReq {
        private Long accommodationSeq;
        private Long reviewSeq;

        public void ofTestSeq(Long seq, Long reviewSeq) {
            this.accommodationSeq = seq;
            this.reviewSeq = reviewSeq;
        }
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
        @Setter
        private String filterType;
        private Long accommodationSeq;
        private AnswerDto.Res answer;

        public static ReqRes changeToDto(Review review) {
            return ReqRes.builder()
                    .totalStarScore((review.getCleanlinessStarScore() + review.getConvenienceStarScore() + review.getKindnessStarScore() + review.getLocationStarScore()) / 4.0)
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
            Review review = Review.builder()
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
    @AllArgsConstructor
    @Setter
    @Getter
    public static class ReviewSum {
        private int cleanliness;
        private int convenience;
        private int kindness;
        private int location;
        private double eachAvgSum = 0.0;
        private Long reviewCnt;

        @QueryProjection
        public ReviewSum(int cleanliness, int convenience, int kindness, int location) {
            this.cleanliness = cleanliness;
            this.convenience = convenience;
            this.kindness = kindness;
            this.location = location;
            this.eachAvgSum = (this.cleanliness + this.convenience + this.kindness + this.location) / 4.0;
        }

        @QueryProjection
        public ReviewSum(int cleanliness, int convenience, int kindness, int location, Long reviewCnt) {
            this.cleanliness = cleanliness;
            this.convenience = convenience;
            this.kindness = kindness;
            this.location = location;
            this.reviewCnt = reviewCnt;
        }
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ReviewSummary {
        private double cleanlinessAvg;
        private double convenienceAvg;
        private double kindnessAvg;
        private double locationAvg;
        private double reviewSum;

        public static ReviewSummary makeSummary(ReviewSum reviewSum) {
            return ReviewSummary.builder()
                    .cleanlinessAvg((double) reviewSum.getCleanliness() / reviewSum.getReviewCnt())
                    .convenienceAvg((double) reviewSum.getConvenience() / reviewSum.getReviewCnt())
                    .kindnessAvg((double) reviewSum.getKindness() / reviewSum.getReviewCnt())
                    .locationAvg((double) reviewSum.getLocation() / reviewSum.getReviewCnt())
                    .build();
        }
    }
}
