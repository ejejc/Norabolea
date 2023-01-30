package com.example.jpamaster.accommodations.repository.review;

import com.example.jpamaster.accommodations.domain.entity.QReview;
import com.example.jpamaster.accommodations.domain.entity.QRoom;
import com.example.jpamaster.accommodations.domain.entity.Review;
import com.example.jpamaster.accommodations.dto.QReviewDto_ReviewSum;
import com.example.jpamaster.accommodations.dto.ReviewDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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
    public Page<Review> findAllReviewByRoomListForPaging(List<Long> roomseqList, Pageable pageable, ReviewDto.ReqRes req) {
        List<Review> fetch = jpaQueryFactory.selectFrom(QReview.review)
                .join(QReview.review.room, QRoom.room).fetchJoin()
                .where(QReview.review.room.roomSeq.in(roomseqList))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewSort(req).toArray(OrderSpecifier[]::new)) // TODO: null처리 좀 더 좋은 방법 없을까?
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(QReview.review.count())
                .from(QReview.review)
                .where(QReview.review.room.roomSeq.in(roomseqList));

        return PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchOne);
    }

    @Override
    public List<ReviewDto.ReviewSum> findAvgEachScore() {
        /**
         * sum() 집계 함수에서 값이 없을 경우, null이 아닌 0으로 반환하기 위해 coalesce() 사용
         * count()는 자동으로 0으로 반환된다.
         */
        return jpaQueryFactory
                .select(
                        new QReviewDto_ReviewSum(QReview.review.room.roomSeq
                        , QReview.review.cleanlinessStarScore.sum()
                        , QReview.review.convenienceStarScore.sum()
                        , QReview.review.kindnessStarScore.sum()
                        , QReview.review.locationStarScore.sum()
                        , QReview.review.room.roomSeq.count())
                ).from(QReview.review)
                .groupBy(QReview.review.room.roomSeq)
                .fetch();
    }

    private List<OrderSpecifier<?>> reviewSort(ReviewDto.ReqRes req) {
        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(req) && !ObjectUtils.isEmpty(req.getFilterType())) {
            switch (req.getFilterType()) {
                case "highScore":
                    orderSpecifierList.add(new OrderSpecifier(Order.DESC, QReview.review.avgStartScore));
                case "lowScore":
                    orderSpecifierList.add(new OrderSpecifier(Order.ASC, QReview.review.avgStartScore));
            }
        } else {
            orderSpecifierList.add(new OrderSpecifier<>(Order.DESC, QReview.review.bestYn));
        }
        orderSpecifierList.add(new OrderSpecifier(Order.DESC, QReview.review.createdAt));

        return orderSpecifierList;
    }

    @Override
    public List<Review> findAllReviewByRoomListToBest(List<Long> roomSeqList) {
        return jpaQueryFactory
                .selectFrom(QReview.review)
                .where(QReview.review.room.roomSeq.in(roomSeqList)
                        .and(QReview.review.bestYn.eq(true))
                )
                .orderBy(QReview.review.createdAt.asc()).fetch();
    }
}
