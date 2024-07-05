package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO inventories(date_create, ticket_code, user_id) VALUES (:createdDate, :code, :userId)", nativeQuery = true)
    void saveInventory(@Param("userId") Long userId, @Param("createdDate") LocalDate createdDate, @Param("code") String code);
    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Long getLastInsertId();
}
