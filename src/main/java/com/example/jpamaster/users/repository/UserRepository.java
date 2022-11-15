package com.example.jpamaster.users.repository;

import com.example.jpamaster.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
