package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.ReservationBucketTokenType;
import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationBucketTokenTypeRepository extends JpaRepository<ReservationBucketTokenType, BucketTokenType> {

}
