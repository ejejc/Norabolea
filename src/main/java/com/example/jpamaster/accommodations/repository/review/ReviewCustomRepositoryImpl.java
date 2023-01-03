package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.QReview;
import com.example.jpamaster.accommodations.domain.entity.Review;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public ReviewCustomRepositoryImpl(EntityManager em) {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findAllReviewByRoomSeq(Long seq) {
        return jpaQueryFactory.selectFrom(QReview.review)
                .where(QReview.review.room.roomSeq.eq(seq))
                .fetch();
    }

    @Override
    public List<Tuple> findAvgEachScore(Long roomSeq) {
        return jpaQueryFactory.select(
                QReview.review.cleanlinessStarScore.sum().as("cleanlinessSum"),
                QReview.review.convenienceStarScore.sum().as("convenienceSum"),
                QReview.review.kindnessStarScore.sum().as("kindnessSum"),
                QReview.review.locationStarScore.sum().as("locationSum"))
                .from(QReview.review)
                .where(QReview.review.room.roomSeq.eq(roomSeq))
                .fetch();
    }
}
