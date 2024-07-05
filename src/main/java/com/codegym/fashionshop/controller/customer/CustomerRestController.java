package com.codegym.fashionshop.controller.customer;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/customer")
@CrossOrigin("*")
public class CustomerRestController {
    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map< String, String > errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        if (iCustomerService.existsByCustomerCode(customer.getCustomerCode())) {
            throw new HttpExceptions.BadRequestException("Ma khach hang da ton tai !");
        }
        if (iCustomerService.existsByEmail(customer.getEmail())) {
            throw new HttpExceptions.BadRequestException("Email da ton tai !");
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>("Them moi khach hang thanh cong", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            iCustomerService.updateCustomer(id, customer);
            return new ResponseEntity<>("Cap nhat khach hang thanh cong !", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi máy chủ nội bộ !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
    public ResponseEntity< Page< Customer > > findAll(@PageableDefault(page = 0, size = 5) Pageable pageable,
                                                      @RequestParam(required = false, defaultValue = "") Long customerId,
                                                      @RequestParam(required = false, defaultValue = "") String customerCode,
                                                      @RequestParam(required = false, defaultValue = "") String customerName,
                                                      @RequestParam(required = false, defaultValue = "") String phoneNumber,
                                                      @RequestParam(required = false, defaultValue = "customerId") String sort) {
        Sort sort1 = Sort.by(Sort.Direction.ASC, sort);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort1);
        Page< Customer > list = iCustomerService.searchCustomer(customerId, "%" + customerCode + "%", "%" + customerName + "%", "%" + phoneNumber + "%", pageableWithSort);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}