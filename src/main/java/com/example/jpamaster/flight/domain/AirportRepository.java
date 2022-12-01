package com.example.jpamaster.flight.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query(
            " select a " +
                    " from Airport a " +
                    " where a.nameEn like %:keyword% " +
                    "   or a.nameKr like %:keyword% " +
                    "   or a.IATACode like %:keyword% " +
                    "   or a.ICAOCode like %:keyword% " +
                    "   or a.location like %:keyword% " +
                    "   or a.countryEn like %:keyword% " +
                    "   or a.countryKr like %:keyword% " +
                    "   or a.cityEn like %:keyword% " +
                    " order by a.searchCount desc "
    )
    List<Airport> findTop3BySearchCondition(@Param("keyword") String keyword, Pageable pageable);

}
