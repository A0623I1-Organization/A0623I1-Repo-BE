package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.entities.ProductImage;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * REST controller for managing products.
 * Provides endpoints for retrieving, creating, and generating product-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/products")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    /**
     * GET endpoint to retrieve a page of products based on search keyword, sorting criteria, and pagination.
     *
     * @param keyword   the keyword to search products by
     * @param sortBy    the field to sort products by (optional)
     * @param ascending whether to sort in ascending order (default true)
     * @param page      the page number (default 0)
     * @return a ResponseEntity containing a page of products and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no products are found
     */
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProduct(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Product> products;
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isEmpty()) {
            sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        }
        products = productService.searchAndSortProducts(keyword, PageRequest.of(page, 10, sort));

        if (products.isEmpty()) {
            products = Page.empty();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new product.
     *
     * @param product       the product to create
     * @param bindingResult the result of the validation
     * @return a ResponseEntity containing the created product and HTTP status CREATED (201) if successful
     * @throws HttpExceptions.BadRequestException if there are validation errors
     */

    @PostMapping("")
    public ResponseEntity<Object> createProduct(@RequestBody @Validated Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        // Set relationships for pricing and product images
        for (Pricing pricing : product.getPricingList()) {
            pricing.setProduct(product);
        }
        for (ProductImage productImage : product.getProductImages()) {
            productImage.setProduct(product);
        }
        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * POST endpoint to generate and check a unique product code.
     *
     * @return a ResponseEntity containing a map with the generated product code and HTTP status OK (200) if successful
     */
    @PostMapping("/generateAndCheckProductCode")
    public ResponseEntity<Map<String, String>> generateAndCheckProductCode() {
        String productCode = generateUniqueProductCode();
        Map<String, String> response = new HashMap<>();
        response.put("code", productCode);
        return ResponseEntity.ok(response);    
    }

    @PostMapping("/checkProductCode")
    public ResponseEntity<Map<String, Boolean>> checkProductCode(@RequestBody Map<String, String> request) {
        String productCode = request.get("code");
        System.out.println("Received pricing code: " + productCode);
        boolean isUnique = productService.isProductCodeUnique(productCode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    /**
     * Generates a unique product code.
     *
     * @return a unique product code
     */
    private String generateUniqueProductCode() {
        String productCode;
        Random random = new Random();
        do {
            productCode = "P" + String.format("%06d", random.nextInt(1000000));
        } while (!productService.isProductCodeUnique(productCode));
        return productCode;
    }
}
