package com.codegym.fashionshop.controller.customers;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @GetMapping
    public ResponseEntity<Page<Customer>> getAllPricing(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Customer> customers = iCustomerService.findAll(PageRequest.of(page, 2));
        if (customers.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
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
