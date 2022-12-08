package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByAirlineIataAndAirlineIcao(String airlineIata, String airlineIcao);


    @Modifying( flushAutomatically = true, clearAutomatically = true)
    @Query(
            " update Airline al set al.deleted = true " +
                    " where al.airlineSeq = :airlineSeq"
    )
    void updateToDeletedByAirlineSeq(@Param("airlineSeq") Long airlineSeq);

}
