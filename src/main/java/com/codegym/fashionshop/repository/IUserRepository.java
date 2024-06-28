package com.codegym.fashionshop.repository;

import com.codegym.fashionshop.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
    @Query("SELECT u FROM AppUser u WHERE " +
            "u.username LIKE %:searchContent% OR " +
            "u.userCode LIKE %:searchContent% OR " +
            "u.role.roleName LIKE %:searchContent%")
    Page<AppUser> searchAppUserByUsernameOrUserCodeOrRoleName(@Param("searchContent") String searchContent, Pageable pageable);
}