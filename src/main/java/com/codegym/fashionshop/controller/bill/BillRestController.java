package com.codegym.fashionshop.controller.bill;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin("*")
public class BillRestController {
    @Autowired
    private IBillService billService;
    @PostMapping("")
    public ResponseEntity<Object> createBill(@Validated @RequestBody Bill bill, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Trả về phản hồi JSON chứa thông điệp lỗi
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        billService.save(bill);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }
}
