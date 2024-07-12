package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IPricingService;
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

    @Autowired
    private IPricingService iPicingService;

    @GetMapping("/new")
    public ResponseEntity<Page<Pricing>> getAllPricingNew(@RequestParam(name = "page", defaultValue = "0") int page) {

        final int MAX_PAGES = 4;
        final int PAGE_SIZE = 5;

        if (page < 0) {
            page = 0;
        }
        if (page >= MAX_PAGES) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Page<Pricing> pricings = iPicingService.findAllPricing(PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id")));
        if (pricings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

    @GetMapping("/nam-nu")
    public ResponseEntity<Page<Pricing>> getAllPricingGender(@RequestParam(name = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Pricing> pricings = iPicingService.findAllPricing(PageRequest.of(page, 10));
        if (pricings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pricings, HttpStatus.OK);
    }

}
