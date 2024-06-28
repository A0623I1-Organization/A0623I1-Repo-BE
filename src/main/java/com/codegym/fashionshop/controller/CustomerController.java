package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        iCustomerService.updateCustomer(id,customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
