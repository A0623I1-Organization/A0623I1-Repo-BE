package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.bill.IBillService;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/customer")
@CrossOrigin("*")
public class CustomerRestController {
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IBillService billService;
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
            throw new HttpExceptions.BadRequestException("Ma khach hang da ton tai !");
        }
        if (iCustomerService.existsByEmail(customer.getEmail())) {
            throw new HttpExceptions.BadRequestException("Email da ton tai !");
        }
        iCustomerService.createCustomer(customer);
        return new ResponseEntity<>("Them moi khach hang thanh cong", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer( @PathVariable Long id, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        try {
            iCustomerService.updateCustomer(id,customer);
            return new ResponseEntity<>("Cap nhat khach hang thanh cong !",HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Lỗi máy chủ nội bộ !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping ("/{id}")
    public ResponseEntity<?> findByIdCustomer(@PathVariable Long id){
        Customer customer = iCustomerService.findById(id);
        if(customer == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomer(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Customer> customers = iCustomerService.findAll(PageRequest.of(page, 2));
        for (Customer customer: customers) {
            System.out.println("ID : " +customer.getCustomerName()+" TotalBill = "+ billService.calculateTotalBillForCustomer(customer.getCustomerId()) );
        }
        if (customers.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin khách hàng");
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}