package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Answer;
import com.example.jpamaster.accommodations.domain.entity.Review;
import lombok.Getter;

public class AnswerDto {

    @Getter
    public static class Req {
        private Long reviewSeq;
        private String content;
        private Long regAdminSeq;

        public Answer toEntity(Review review) {
            return Answer.builder()
                    .content(this.content)
                    .review(review)
                    .deleteYn(false).build();
        }
    }
}
