package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.service.customer.ICustomerService;
import com.codegym.fashionshop.service.customer.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/customer")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer( @PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        iCustomerService.updateCustomer(id,customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<?> findByIdCustomer(@PathVariable Long id){
        Customer customer = iCustomerService.findById(id);
        if(customer == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
}
