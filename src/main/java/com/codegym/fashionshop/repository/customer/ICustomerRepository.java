package com.codegym.fashionshop.repository.customer;

import com.codegym.fashionshop.entities.Customer;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * The ICustomerRepository interface provides methods for managing customer data.
 *
 * <p>This interface extends JpaRepository to provide basic CRUD operations and
 * defines custom queries for creating and updating customers.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * Finds all customers with pagination support.
     *
     * @param pageable the pagination information
     * @return a paginated list of customers
     */
    Page<Customer> findAll(Pageable pageable);

    /**
     * Creates a new customer with the specified details.
     *
     * @param customerCode      the customer code
     * @param customerName      the customer name
     * @param dateOfBirth       the date of birth
     * @param gender            the gender
     * @param email             the email
     * @param phoneNumber       the phone number
     * @param address           the address
     * @param dateRegister      the registration date
     * @param customerCode the customer code
     * @param customerName the customer name
     * @param dateOfBirth the date of birth
     * @param gender the gender
     * @param email the email
     * @param phoneNumber the phone number
     * @param address the address
     * @param dateRegister the registration date
     * @param accumulatedPoints the accumulated points
     */
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

    /**
     * Updates an existing customer with the specified details.
     *
     * @param id                the ID of the customer to be updated
     * @param customerName      the customer name
     * @param dateOfBirth       the date of birth
     * @param gender            the gender
     * @param email             the email
     * @param phoneNumber       the phone number
     * @param address           the address
     * @param customerTypeId    the customer type
     * @param id the ID of the customer to be updated
     * @param customerName the customer name
     * @param dateOfBirth the date of birth
     * @param gender the gender
     * @param email the email
     * @param phoneNumber the phone number
     * @param address the address
     * @param customerTypeId the customer type
     * @param accumulatedPoints the accumulated points
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE customers SET customer_name = :customerName, date_of_birth = :dateOfBirth, gender = :gender, email = :email, phone_number = :phoneNumber, address = :address, type_id = :customerType, accumulated_points = :accumulatedPoints WHERE customer_id =:id", nativeQuery = true)

    void updateCustomer(@Param("id") Long id,
                        @Param("customerName") String customerName,
                        @Param("dateOfBirth") LocalDate dateOfBirth,
                        @Param("gender") Integer gender,
                        @Param("email") String email,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("address") String address,
                        @Param("customerTypeId") Long customerTypeId,
                        @Param("accumulatedPoints") int accumulatedPoints
    );


    /**
     * Checks if a customer with the specified customer code exists.
     *
     * @param customerCode the customer code
     * @return true if a customer with the specified customer code exists, false otherwise
     */
    boolean existsByCustomerCode(String customerCode);

    /**
     * Checks if a customer with the specified email exists.
     *
     * @param email the email
     * @return true if a customer with the specified email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a customer with the specified email exists, excluding a customer with the specified customer code.
     *
     * @param email        the email
     * @param email the email
     * @param customerCode the customer code to exclude
     * @return true if a customer with the specified email exists, excluding the specified customer code, false otherwise
     */
    boolean existsByEmailAndCustomerCodeNot(String email, String customerCode);

    @Query("select c from Customer c where c.customerCode like :customerCode and c.customerName like :customerName and c.phoneNumber like :phoneNumber")
    Page< Customer > searchCustomer(@Param("customerCode") String customerCode, @Param("customerName") String customerName, @Param("phoneNumber") String phoneNumber, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Customer c where c.customerId = :customerId")
    void deleteCustomer(@Param("customerId") Long customerId);

    @Query("select c from Customer c")
    List< Customer > getAllCustomer();
}
