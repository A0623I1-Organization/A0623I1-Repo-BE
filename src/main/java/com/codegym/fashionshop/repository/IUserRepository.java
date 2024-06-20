package com.codegym.fashionshop.repository;

import com.codegym.fashionshop.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
}