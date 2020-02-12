package com.usbtc.kiosk.repositories;

import com.usbtc.kiosk.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Integer> {
  public boolean existsByUsername(String username);

  public boolean existsByEmail(String email);

  public User findByUsername(String username);
}