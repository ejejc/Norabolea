package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.Airline;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Airline> findByAirlineIataAndAirlineIcao(String airlineIata, String airlineIcao);


    @Modifying( flushAutomatically = true, clearAutomatically = true)
    @Query(
            " update Airline al set al.deleted = true " +
                    " where al.airlineSeq = :airlineSeq"
    )
    void updateToDeletedByAirlineSeq(@Param("airlineSeq") Long airlineSeq);


    @Query(
            " select al " +
                    " from Airline al " +
                    " where lower(al.airlineIata) like %:keyword%  " +
                    "             or lower(al.airlineIcao) like %:keyword% " +
                    "             or lower(al.airlineName) like %:keyword% "
    )
    Page<Airline> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
