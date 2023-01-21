package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.Airport;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query(
            " select a " +
                    " from Airport a " +
                    " where lower(a.nameEn)  like %:keyword% " +
                    "   or lower(a.nameKr)  like %:keyword% " +
                    "   or lower(a.IATACode) like %:keyword% " +
                    "   or lower(a.ICAOCode)  like %:keyword% " +
                    "   or lower(a.locationKr)  like %:keyword% " +
                    "   or lower(a.locationEn)  like %:keyword% " +
                    "   or lower(a.countryEn)  like %:keyword% " +
                    "   or lower(a.countryKr)  like %:keyword% " +
                    "   or lower(a.cityEn)  like %:keyword% " +
                    " order by a.searchCount desc "
    )
    List<Airport> findTop3BySearchCondition(@Param("keyword") String keyword, Pageable pageable);

    Optional<Airport> findByAirportSeq(Long fromAirportSeq);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Airport> findByIATACodeAndICAOCode(String IATACode, String ICAOCode);

    Optional<Airport> findByIATACode(String iataCode);
}
