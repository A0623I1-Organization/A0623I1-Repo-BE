package com.codegym.fashionshop.controller.bill;


import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.bill.IBillItemService;
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
@RequestMapping("/api/bill-items")
@CrossOrigin("*")
public class BillItemRestController {
    @Autowired
    private IBillItemService billItemService;
    @PostMapping("")
    public ResponseEntity<Object> createBillItem(@Validated @RequestBody BillItem billItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Trả về phản hồi JSON chứa thông điệp lỗi
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        billItemService.save(billItem);
        return new ResponseEntity<>(billItem, HttpStatus.CREATED);
    }
}
