package com.codegym.fashionshop.service.promotion.impl;

import com.codegym.fashionshop.entities.Promotion;
import com.codegym.fashionshop.repository.promotion.IPromotionRepository;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService implements IPromotionService {
    @Autowired
    private IPromotionRepository promotionRepository;
    @Override
    public Promotion findByPromotionCode(String promotionCode) {
        return promotionRepository.findByPromotionCode(promotionCode);
    }
}
