package com.example.jpamaster.accommodations.repository;


import com.example.jpamaster.accommodations.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
