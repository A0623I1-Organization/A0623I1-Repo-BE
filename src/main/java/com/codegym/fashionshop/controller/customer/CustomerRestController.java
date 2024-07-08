package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.dto.respone.ErrorDetail;
import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @param customer      the customer to be created
     * @param bindingResult the result of the validation binding
     * @param customer       the customer to be created
     * @param bindingResult  the result of the validation binding
     * @return ResponseEntity with the result of the creation operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or customer code/email already exists
     */
    @PostMapping("/create")
    public ResponseEntity< ? > createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (iCustomerService.existsByCustomerCode(customer.getCustomerCode())) {
            ErrorDetail errors = new ErrorDetail("Customer code already exists");
            errors.addError("customerCode", "Mã khách hàng đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (iCustomerService.existsByEmail(customer.getEmail())) {
            ErrorDetail errors = new ErrorDetail("Email already exists");
            errors.addError("email", "Email đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer.
     *
     * @param id            the ID of the customer to be updated
     * @param customer      the updated customer data
     * @param bindingResult the result of the validation binding
     * @param id             the ID of the customer to be updated
     * @param customer       the updated customer data
     * @param bindingResult  the result of the validation binding
     * @return ResponseEntity with the result of the update operation
     * @throws HttpExceptions.BadRequestException if validation errors occur or email already exists
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorDetail errors = new ErrorDetail("Validation errors");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.addError(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (iCustomerService.existsByEmailAndCustomerCodeNot(customer.getEmail(), customer.getCustomerCode())) {
            ErrorDetail errors = new ErrorDetail("Email already exists");
            errors.addError("email", "Email đã tồn tại !");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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


    @DeleteMapping("/{customerId}")
    public ResponseEntity< Void > deleteCustomer(@PathVariable Long customerId) {

        iCustomerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity< List< Customer > > getAllCustomer() {
        List< Customer > customerList = iCustomerService.getAllCustomers();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/search")
    public ResponseEntity< Page< Customer > > searchCustomer(@PageableDefault(page = 0, size = 5) org.springframework.data.domain.Pageable pageable,
                                                             @RequestParam(required = false, defaultValue = "") String customerName,
                                                             @RequestParam(required = false, defaultValue = "") String customerCode,
                                                             @RequestParam(required = false, defaultValue = "") String phoneNumber,
                                                             @RequestParam(required = false, defaultValue = "customerId") String sort) {
        Sort sort1 = Sort.by(Sort.Direction.ASC, sort);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort1);
        Pageable p = PageRequest.of(0, 5, Sort.Direction.ASC, "customerId");
        Page<Customer> list = iCustomerService.searchCustomer("%" + customerCode + "%", "%" + customerName + "%", "%" + phoneNumber + "%", p);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
