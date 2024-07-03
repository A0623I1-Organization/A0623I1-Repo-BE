package com.codegym.fashionshop.repository.customer;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.entities.CustomerType;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c")

    Page<Customer> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customers (customer_code, customer_name, date_of_birth, gender, email, phone_number, address, date_register, accumulated_points, type_id) VALUES (:customerCode, :customerName, :dateOfBirth, :gender, :email, :phoneNumber, :address, :dateRegister, :accumulatedPoints, 1)", nativeQuery = true)
    void createCustomer(@Param("customerCode") String customerCode,
                        @Param("customerName") String customerName,
                        @Param("dateOfBirth") LocalDate dateOfBirth,
                        @Param("gender") Integer gender,
                        @Param("email") String email,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("address") String address,
                        @Param("dateRegister") LocalDate dateRegister,
                        @Param("accumulatedPoints") int accumulatedPoints
    );

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.customerName = :customerName, c.dateOfBirth = :dateOfBirth, c.gender = :gender, c.email = :email, c.phoneNumber = :phoneNumber, c.address = :address, c.customerType = :customerType, c.accumulatedPoints = :accumulatedPoints WHERE c.customerId = :id")
    void updateCustomer(@Param("id") Long id,
                        @Param("customerName") String customerName,
                        @Param("dateOfBirth") LocalDate dateOfBirth,
                        @Param("gender") Integer gender,
                        @Param("email") String email,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("address") String address,
                        @Param("customerType") CustomerType customerType,
                        @Param("accumulatedPoints") int accumulatedPoints
    );

}
