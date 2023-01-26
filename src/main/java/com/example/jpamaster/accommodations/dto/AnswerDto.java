package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Answer;
import com.example.jpamaster.accommodations.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;
import java.time.LocalDate;

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

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Res {
        private Long answerSeq;
        private String content;

        private LocalDate createdAt;

        public static Res toDto(Answer answer) {
            if (!ObjectUtils.isEmpty(answer)) {
                return Res.builder()
                        .answerSeq(answer.getAnswerSeq())
                        .content(answer.getContent())
                        .createdAt(answer.getCreatedAt().toLocalDate()).build();
            }
            return null;
        }
    }
}
