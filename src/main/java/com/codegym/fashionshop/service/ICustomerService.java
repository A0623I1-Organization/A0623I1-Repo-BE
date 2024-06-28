package com.codegym.fashionshop.service;

import com.codegym.fashionshop.entities.Customer;

public interface ICustomerService {
    void createCustomer(Customer customer);

    void updateCustomer(Long id,Customer customer);
}
