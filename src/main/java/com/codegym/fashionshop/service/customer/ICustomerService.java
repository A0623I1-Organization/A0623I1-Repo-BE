package com.codegym.fashionshop.service.customer;

import com.codegym.fashionshop.entities.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The ICustomerService interface provides methods for managing customer data.
 *
 * <p>This interface defines methods for creating, updating, finding, and checking the existence of customers.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
public interface ICustomerService {

    /**
     * Creates a new customer.
     *
     * @param customer the customer to be created
     */
    void createCustomer(Customer customer);

    /**
     * Updates an existing customer with the specified details.
     *
     * @param id the ID of the customer to be updated
     * @param customer the updated customer data
     */
    void updateCustomer(Long id, Customer customer);

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return the customer with the specified ID, or null if no such customer exists
     */
    Customer findById(Long id);

    /**
     * Finds all customers with pagination support.
     *
     * @param pageable the pagination information
     * @return a paginated list of customers
     */
    Page<Customer> findAll(Pageable pageable);

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
     * Checks if a customer exists with the given email but not with the specified customer code.
     *
     * @param email        the email address of the customer to check.
     * @param customerCode the customer code to exclude in the check.
     * @return {@code true} if a customer exists with the given email but not with the specified customer code, {@code false} otherwise.
     */
    boolean existsByEmailAndCustomerCodeNot(String email, String customerCode);

    void deleteCustomer(Long customerId);
    List<Customer> getAllCustomers();
    Page<Customer> searchCustomer(String customerCode, String customerName, String phoneNumber,Pageable pageable);
}
