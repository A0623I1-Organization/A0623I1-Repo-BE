package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.entities.ProductImage;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProduct(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Product> products = productService.findAllProduct(PageRequest.of(page, 2));
        if (products.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin giá");
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Object> createProduct( @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Trả về phản hồi JSON chứa thông điệp lỗi
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        for (Pricing pricing : product.getPricingList()){
            pricing.setProduct(product);
        }
        for (ProductImage productImage: product.getProductImages()) {
            productImage.setProduct(product);
        }
        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}
