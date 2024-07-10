package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("")
    public ResponseEntity<Page<Pricing>> getAllPricing(@RequestParam(name = "page", defaultValue = "0") int page) {
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
