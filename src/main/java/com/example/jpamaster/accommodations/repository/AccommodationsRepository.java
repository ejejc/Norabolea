package com.example.jpamaster.accommodations.repository;

import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationsRepository extends JpaRepository<Accommodations, Long> {
}
