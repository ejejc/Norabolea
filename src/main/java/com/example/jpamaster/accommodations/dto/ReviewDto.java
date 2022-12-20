package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.ReviewMedia;
import com.example.jpamaster.accommodations.domain.entity.Room;
import lombok.Builder;
import lombok.Getter;
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
}
