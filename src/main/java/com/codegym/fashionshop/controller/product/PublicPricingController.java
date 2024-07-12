package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IPricingService;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public/pricings")
@CrossOrigin("*")
public class PublicPricingController {

//    @Autowired
//    private IPricingService iPicingService;

    @Autowired
    private IProductService iProductService;

    @GetMapping("/new")
    public ResponseEntity<?> getAllPricingNew(@RequestParam(name = "page", defaultValue = "0") int page) {

        final int MAX_PAGES = 2;
        final int PAGE_SIZE = 5;

        if (page < 0) {
            page = 0;
        }
        if (page >= MAX_PAGES) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Page<Product> pricings = iProductService.findAllProduct(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "productId")));
        if (pricings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @GetMapping("/nam-nu")
    public ResponseEntity<?> getAllPricingGender(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Product> pricings = iProductService.findAllProduct(PageRequest.of(page, 10));
        if (pricings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

}
