package com.example.jpamaster.flight.domain.repository.airschedule;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirScheduleRepository extends JpaRepository<AirSchedule, Long>, AirScheduleCustomRepository {
}
