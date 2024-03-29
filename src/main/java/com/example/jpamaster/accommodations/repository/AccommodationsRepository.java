package com.example.jpamaster.accommodations.repository;

import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccommodationsRepository extends JpaRepository<Accommodations, Long> {

    //@EntityGraph(attributePaths = "accommoFacilityInfos")
    //Optional<Accommodations> findByAccommodationSeq(Long accommodationSeq);
}
