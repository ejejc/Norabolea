package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.domain.entity.ReviewMedia;
import com.example.jpamaster.accommodations.domain.entity.Room;
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
