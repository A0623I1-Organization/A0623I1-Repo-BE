package com.codegym.fashionshop.controller.promotion;

import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Promotion;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/promotions")
@CrossOrigin("*")
public class PromotionRestController {
    @Autowired
    private IPromotionService promotionService;
    @GetMapping
    public ResponseEntity<Promotion> getPromotionByCode(@RequestParam(name = "promotionCode") String promotionCode) {
        Promotion promotion = promotionService.findByPromotionCode(promotionCode);
        if (promotion == null) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin khuyến mãi");
        }
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @PostMapping("/use")
    public ResponseEntity<Promotion> usePromotion(@RequestParam(name = "promotionCode") String promotionCode) {
        Promotion promotion = promotionService.usePromotion(promotionCode);
        if (promotion == null) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin khuyến mãi hoặc khuyến mãi không khả dụng");
        }
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }
}
