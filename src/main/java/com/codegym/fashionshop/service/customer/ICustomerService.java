package com.codegym.fashionshop.service.customer;

import com.codegym.fashionshop.entities.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICustomerService {
    void createCustomer(Customer customer);

    void updateCustomer(Long id,Customer customer);
    Customer findById(Long id);
    Page<Customer> findAll(Pageable pageable);
    boolean existsByCustomerCode(String customerCode);
    boolean existsByEmail(String email);
}
