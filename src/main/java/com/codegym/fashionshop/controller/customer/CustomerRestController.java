package com.codegym.fashionshop.controller.customer;

import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/customer")
@CrossOrigin("*")
public class CustomerRestController {
    @Autowired
    private ICustomerService iCustomerService;
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        }
        try {
            iCustomerService.createCustomer(customer);
            return new ResponseEntity<>("Them moi khach hang thanh cong", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi máy chủ nội bộ !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
}