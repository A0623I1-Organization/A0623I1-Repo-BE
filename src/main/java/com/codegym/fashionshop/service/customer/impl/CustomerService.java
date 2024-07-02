package com.codegym.fashionshop.service.customer.impl;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.entities.CustomerType;
import com.codegym.fashionshop.repository.customer.ICustomerRepository;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Override
    public void createCustomer(Customer customer) {
        System.out.println(customer);
        customer.setCustomerType(new CustomerType(1L,""));
        customer.setAccumulatedPoints(0);
        customer.setDateRegister(LocalDate.now());

        iCustomerRepository.createCustomer(
                customer.getCustomerCode(),
                customer.getCustomerName(),
                customer.getDateOfBirth(),
                customer.getGender(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getDateRegister(),
                customer.getAccumulatedPoints()
        );
    }

    @Override
    public void updateCustomer(Long id, Customer customer) {
        if (iCustomerRepository.existsById(id)) {
            iCustomerRepository.updateCustomer(
                    id,
                    customer.getCustomerName(),
                    customer.getDateOfBirth(),
                    customer.getGender(),
                    customer.getEmail(),
                    customer.getPhoneNumber(),
                    customer.getAddress(),
                    customer.getCustomerType(),
                    customer.getAccumulatedPoints()
            );
        } else {
            throw new IllegalArgumentException("Customer with ID " + id + " does not exist.");
        }
    }

    @Override
    public Customer findById(Long id) {
        return iCustomerRepository.findById(id).orElse(null);
    }
}
