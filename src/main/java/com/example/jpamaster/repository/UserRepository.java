package com.example.jpamaster.repository;

import com.example.jpamaster.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
