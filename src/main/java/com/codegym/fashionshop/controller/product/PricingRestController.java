package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.dto.WarehouseReceiptDTO;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
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
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * REST controller for managing pricings.
 * Provides endpoints for retrieving, creating, and generating pricing-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/pricing")
@CrossOrigin("*")
public class PricingRestController {

    @Autowired
    private IPricingService pricingService;
    @Autowired
    private IAppUserService appUserService;

    /**
     * GET endpoint to retrieve a page of pricings.
     *
     * @param page the page number (default 0)
     * @return a ResponseEntity containing a page of pricings and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no pricings are found
     */
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

    /**
     * GET endpoint to retrieve pricing by pricing code.
     *
     * @param pricingCode the pricing code to search for
     * @return a ResponseEntity containing the found pricing and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no pricing is found
     */
    @GetMapping("/byCode")
    public ResponseEntity<Pricing> getPricingByPricingCode(@RequestParam(name = "pricingCode") String pricingCode) {
        Pricing pricing = pricingService.findByPricingCode(pricingCode);
        if (pricing == null) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(pricing, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve all pricings by product ID.
     *
     * @param productId the product ID to search pricings for
     * @param page      the page number (default 0)
     * @return a ResponseEntity containing a page of pricings and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no pricings are found
     */
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

    /**
     * POST endpoint to create a new pricing.
     *
     * @param pricing       the pricing to create
     * @param bindingResult the result of the validation
     * @return a ResponseEntity containing the created pricing and HTTP status CREATED (201) if successful
     * @throws HttpExceptions.BadRequestException if there are validation errors
     */
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
     * Retrieving a pricings list.
     *
     * <p>This method generates a list of all pricings.
     *
     * @return A ResponseEntity containing the pricings list.
     * @author ThanhTT
     */
    @GetMapping("/list")
    public ResponseEntity<?> getAllPricing() {
        List<Pricing> pricings = pricingService.findAllPricing();
        if(pricings.isEmpty()) {
            return new ResponseEntity<>("No pricings list found", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
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
    public ResponseEntity<WarehouseReceiptDTO> getPricingListWithUserAndDate() {
        List<Pricing> pricings = new ArrayList<>();
        LocalDate date = LocalDate.now();
        String id = UUID.randomUUID().toString();
        WarehouseReceiptDTO receipt = WarehouseReceiptDTO.builder().receiptId(id).date(date).pricingList(pricings).build();
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
    public ResponseEntity<?> updatePricingQuantity(@RequestBody WarehouseReceiptDTO warehouseReceipt) {
        if (warehouseReceipt == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pricingService.updatePricingQuantity(warehouseReceipt);
        return ResponseEntity.ok().build();
    }

    /**
     * POST endpoint to generate and check a unique pricing code.
     *
     * @return a ResponseEntity containing a map with the generated pricing code and HTTP status OK (200) if successful
     */
    @PostMapping("/generateAndCheckPricingCode")
    public ResponseEntity<Map<String, String>> generateAndCheckPricingCode() {
        String pricingCode = generateUniquePricingCode();
        Map<String, String> response = new HashMap<>();
        response.put("code", pricingCode);
        return ResponseEntity.ok(response);
    }

    /**
     * Generates a unique pricing code.
     *
     * @return a unique pricing code
     */
    private String generateUniquePricingCode() {
        String pricingCode;
        Random random = new Random();
        do {
            pricingCode = "H" + String.format("%06d", random.nextInt(1000000));
        } while (!pricingService.isPricingCodeUnique(pricingCode));
        return pricingCode;
    }
}
