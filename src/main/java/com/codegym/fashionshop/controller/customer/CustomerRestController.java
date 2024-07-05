package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The CustomerRestController class handles HTTP requests for managing customers.
 *
 * <p>This class provides RESTful endpoints for creating, updating, and retrieving customer information.</p>
 *
 * <p>Author: [QuyLV]</p>
 */
@RestController
@RequestMapping("/api/auth/customer")
@CrossOrigin("*")
public class CustomerRestController {

    @Autowired
    private ICustomerService iCustomerService;

    /**
     * Creates a new customer.
     *
     * @param customer the customer to be created
     * @param bindingResult the result of the validation binding
     * @return ResponseEntity with the result of the creation operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or customer code/email already exists
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        if (iCustomerService.existsByCustomerCode(customer.getCustomerCode())) {
            throw new HttpExceptions.BadRequestException("Customer code already exists!");
        }
        if (iCustomerService.existsByEmail(customer.getEmail())) {
            throw new HttpExceptions.BadRequestException("Email already exists!");
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer.
     *
     * @param id the ID of the customer to be updated
     * @param customer the updated customer data
     * @param bindingResult the result of the validation binding
     * @return ResponseEntity with the result of the update operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or email already exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        if (iCustomerService.existsByEmail(customer.getEmail())) {
            throw new HttpExceptions.BadRequestException("Email already exists!");
        }
        iCustomerService.updateCustomer(id, customer);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.CREATED);
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be retrieved
     * @return ResponseEntity with the customer data if found, otherwise NOT_FOUND status
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdCustomer(@PathVariable Long id) {
        Customer customer = iCustomerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
