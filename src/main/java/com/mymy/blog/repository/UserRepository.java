package com.mymy.blog.repository;

import com.mymy.blog.domain.Users;
import com.mymy.blog.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}