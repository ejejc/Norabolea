package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneSeatTypeRepository extends JpaRepository<AirplaneSeatType, Long> {

}
