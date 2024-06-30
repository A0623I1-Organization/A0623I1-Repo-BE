package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.dto.respone.WarehouseReceipt;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.IAppUserService;
import com.codegym.fashionshop.service.product.IPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pricing")
@CrossOrigin("*")
public class PricingRestController {

    @Autowired
    private IPricingService pricingService;
    @Autowired
    private IAppUserService appUserService;

    @GetMapping
    public ResponseEntity<Page<Pricing>> getAllPricing(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Pricing> pricings = pricingService.findAllPricing(PageRequest.of(page, 2));
        if (pricings.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Page<Pricing>> getAllPricingByProductId(@PathVariable(name = "productId")Long productId, @RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Pricing> pricings = pricingService.findAllByProduct_ProductId(productId,PageRequest.of(page, 2));
        if (pricings.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Object> createPricing(@Validated @RequestBody Pricing pricing, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Trả về phản hồi JSON chứa thông điệp lỗi
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        pricingService.createPricing(pricing);
        return new ResponseEntity<>(pricing, HttpStatus.CREATED);
    }

    @GetMapping("/update")
    public ResponseEntity<WarehouseReceipt> getPricingListWithUserAndDate() {
        List<Pricing> pricings = pricingService.findAllPricing();
        LocalDate date = LocalDate.now();
        String id = UUID.randomUUID().toString();
        WarehouseReceipt receipt = WarehouseReceipt.builder().receiptId(id).date(date).pricingList(pricings).build();
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePricingQuantity(@RequestBody WarehouseReceipt warehouseReceipt) {
        pricingService.updatePricingQuantity(warehouseReceipt);
        return ResponseEntity.ok().build();
    }

}
