package com.jmakarevski.hrmanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jmakarevski.hrmanagement.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
