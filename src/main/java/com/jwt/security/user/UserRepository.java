package com.jwt.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Optional<T> is a container object that may or may not contain a non-null value
    Optional<User> findByEmail(String email);
}
