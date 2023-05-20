package com.example.jpamaster.review.repository;

import com.example.jpamaster.accommodations.domain.entity.Review;

import java.util.ArrayList;
import java.util.List;
//ddd
public class ReviewFixture {
    public static Review generateReview() {
        Review review = Review.builder()
                .content("테스트 리뷰예요~")
                .bestYn(false)
                .kindnessStarScore(5)
                .cleanlinessStarScore(3)
                .convenienceStarScore(3)
                .locationStarScore(4).build();
        review.setAvgStartScore(review.calculateAvgStartScore());
        return review;
    }

    public static List<Review> generateReviewList() {
        List<Review> reviewList = new ArrayList<>();
         Review review1 = Review.builder()
                .content("리뷰1")
                .bestYn(false)
                .kindnessStarScore(5)
                .cleanlinessStarScore(3)
                .convenienceStarScore(3)
                .locationStarScore(4).build();
        review1.setAvgStartScore(review1.calculateAvgStartScore());
        reviewList.add(review1);

        Review review2 = Review.builder()
                .content("리뷰2")
                .bestYn(true)
                .kindnessStarScore(1)
                .cleanlinessStarScore(2)
                .convenienceStarScore(3)
                .locationStarScore(1).build();
        review1.setAvgStartScore(review1.calculateAvgStartScore());
        reviewList.add(review2);
        return reviewList;
    }

    public static List<Review> generateBestReviewList() {
        List<Review> reviewList = new ArrayList<>();
        Review review1 = Review.builder()
                .seq(1L)
                .content("리뷰1")
                .bestYn(true)
                .kindnessStarScore(5)
                .cleanlinessStarScore(3)
                .convenienceStarScore(3)
                .locationStarScore(4).build();
        review1.setAvgStartScore(review1.calculateAvgStartScore());
        reviewList.add(review1);

        Review review2 = Review.builder()
                .seq(2L)
                .content("리뷰2")
                .bestYn(true)
                .kindnessStarScore(1)
                .cleanlinessStarScore(2)
                .convenienceStarScore(3)
                .locationStarScore(1).build();
        review1.setAvgStartScore(review1.calculateAvgStartScore());
        reviewList.add(review2);

        Review review3 = Review.builder()
                .seq(3L)
                .content("리뷰2")
                .bestYn(true)
                .kindnessStarScore(1)
                .cleanlinessStarScore(2)
                .convenienceStarScore(3)
                .locationStarScore(1).build();
        review1.setAvgStartScore(review1.calculateAvgStartScore());
        reviewList.add(review3);

        return reviewList;
    }
}
