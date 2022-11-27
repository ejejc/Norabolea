package com.example.jpamaster.accommodations.repository;

import com.example.jpamaster.accommodations.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
}
