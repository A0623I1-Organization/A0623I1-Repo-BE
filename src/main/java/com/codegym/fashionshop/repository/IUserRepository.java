package com.codegym.fashionshop.repository;

import com.codegym.fashionshop.entities.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM app_user WHERE user_name = :username", nativeQuery = true)
    AppUser findByUsername(@Param("username") String username);

    @Query(value = "SELECT u.* FROM app_user u JOIN app_role r on u.role_id = r.role_id WHERE " +
            "u.user_name LIKE %:searchContent% " +
            "OR u.user_code LIKE %:searchContent% " +
            "OR r.role_name LIKE %:searchContent%", nativeQuery = true)
    Page<AppUser> searchAppUserByUsernameOrUserCodeOrRoleName(@Param("searchContent") String searchContent, Pageable pageable);

    @Query(value = "SELECT * FROM app_user", nativeQuery = true)
    List<AppUser> findAll();

    @Query(value = "SELECT * FROM app_user WHERE user_id = :userId", nativeQuery = true)
    Optional<AppUser> findById(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_user WHERE user_id = :userId", nativeQuery = true)
    void deleteById(@Param("userId") Long userId);
}