package com.codegym.fashionshop.repository;

import com.codegym.fashionshop.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String role);
}
