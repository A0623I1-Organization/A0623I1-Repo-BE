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
@RequestMapping("/api/pricing")
@CrossOrigin("*")
public class PricingRestController {

    @Autowired
    private IPricingService pricingService;
    @Autowired
    private IAppUserService appUserService;

    @GetMapping("/all")
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

    @GetMapping("/byCode")
    public ResponseEntity<Pricing> getPricingByPricingCode(@RequestParam(name = "pricingCode") String pricingCode) {
        Pricing pricing = pricingService.findByPricingCode(pricingCode);
        if (pricing == null) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(pricing, HttpStatus.OK);
    }

    @GetMapping("/byProductId/{productId}")
    public ResponseEntity<Page<Pricing>> getAllPricingByProductId(@PathVariable(name = "productId") Long productId,
                                                                  @RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Pricing> pricings = pricingService.findAllByProduct_ProductId(productId, PageRequest.of(page, 2));
        if (pricings.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPricing(@Validated @RequestBody Pricing pricing, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        pricingService.createPricing(pricing);
        return new ResponseEntity<>(pricing, HttpStatus.CREATED);
    }

    /**
     * Generates a warehouse receipt with the current date and a new unique receipt ID.
     *
     * <p>This method generates a new warehouse receipt with the current date and a unique ID,
     * and includes the list of all pricings.
     *
     * @return A ResponseEntity containing the generated warehouse receipt.
     * @author ThanhTT
     */
    @GetMapping("/update")
    public ResponseEntity<WarehouseReceipt> getPricingListWithUserAndDate() {
        List<Pricing> pricings = pricingService.findAllPricing();
        LocalDate date = LocalDate.now();
        String id = UUID.randomUUID().toString();
        WarehouseReceipt receipt = WarehouseReceipt.builder().receiptId(id).date(date).pricingList(pricings).build();
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }

    /**
     * Updates the pricing quantities based on the provided warehouse receipt.
     *
     * <p>This method calls the service to update the pricing quantities and inventory ID based on the
     * provided warehouse receipt.
     *
     * @param warehouseReceipt The warehouse receipt containing the pricing list and other details.
     * @return A ResponseEntity indicating the result of the update operation.
     * @author ThanhTT
     */
    @PutMapping("/update")
    public ResponseEntity<?> updatePricingQuantity(@RequestBody WarehouseReceipt warehouseReceipt) {
        pricingService.updatePricingQuantity(warehouseReceipt);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkPricingCode")
    public ResponseEntity<Map<String, Boolean>> checkPricingCode(@RequestBody Map<String, String> request) {
        String pricingCode = request.get("code");
        boolean isUnique = pricingService.isPricingCodeUnique(pricingCode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }
}
