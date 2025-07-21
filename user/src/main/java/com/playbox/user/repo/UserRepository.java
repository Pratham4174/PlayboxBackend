package com.playbox.user.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playbox.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
  }
  