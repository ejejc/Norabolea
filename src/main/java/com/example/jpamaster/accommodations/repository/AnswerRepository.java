package com.example.jpamaster.accommodations.repository;

import com.example.jpamaster.accommodations.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
