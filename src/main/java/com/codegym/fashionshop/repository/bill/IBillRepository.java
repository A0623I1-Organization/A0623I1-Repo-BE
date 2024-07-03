package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Repository interface for managing bills.
 * Provides methods for CRUD operations and custom queries related to bills.
 * Author: HoaNTT
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

    /**
     * Checks if a bill with the given bill code exists.
     *
     * @param billCode the bill code to check
     * @return true if a bill with the given code exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Bill b WHERE b.billCode = :billCode")
    boolean existsByBillCode(@Param("billCode") String billCode);

    /**
     * Creates a new bill.
     *
     * @param billCode   the bill code
     * @param dateCreate the date of creation
     * @param customerId the ID of the customer associated with the bill
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO bills (bill_code, date_create, customer_id) VALUES (:billCode, :dateCreate, :customerId)", nativeQuery = true)
    void createBill(@Param("billCode") String billCode, @Param("dateCreate") LocalDate dateCreate, @Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.accumulatedPoints = c.accumulatedPoints + :pointsToAdd WHERE c.customerId = :customerId")
    void updateAccumulatedPoints(@Param("customerId") Long customerId, @Param("pointsToAdd") int pointsToAdd);
}
