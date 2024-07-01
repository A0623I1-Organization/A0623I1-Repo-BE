package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.dto.respone.WarehouseReceipt;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.repository.product.IInventoryRepository;
import com.codegym.fashionshop.repository.product.IPricingRepository;
import com.codegym.fashionshop.service.IAppUserService;
import com.codegym.fashionshop.service.product.IPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PricingService implements IPricingService {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IInventoryRepository inventoryRepository;
@Autowired
private IPricingRepository pricingRepository;
    @Override
    public List<Pricing> findAllPricing() {
        return pricingRepository.findAllPricings();
    }

    @Override
    public Page<Pricing> findAllPricing(Pageable pageable) {
        return pricingRepository.findAll(pageable);
    }

    @Override
    public Page<Pricing> findAllByProduct_ProductId(Long productId,Pageable pageable) {
        return pricingRepository.findAllByProduct_ProductId(productId,pageable);
    }

    @Override
    public void createPricing(Pricing pricing) {
        pricingRepository.save(pricing);
    }

    @Override
    public void updatePricingQuantity(WarehouseReceipt warehouseReceipt) {
        AppUser user = appUserService.findByUsername(warehouseReceipt.getUsername());
        inventoryRepository.saveInventory(user.getUserId(), warehouseReceipt.getDate(), warehouseReceipt.getReceiptId());
        Long inventoryId = inventoryRepository.getLastInsertId();
        System.out.println(inventoryId);
        List<Pricing> updatedPricing = warehouseReceipt.getPricingList();
        for (Pricing p: updatedPricing) {
            pricingRepository.updateQuantityAndInventory(p.getPricingId(), p.getQuantity(),inventoryId);
    }

    public boolean isPricingCodeUnique(String pricingCode) {
        return !pricingRepository.existsByPricingCode(pricingCode);
    }

    @Override
    public Pricing findByPricingCode(String pricingCode) {
        return pricingRepository.findByPricingCode(pricingCode);

    public void updatePricingQuantity(Long id, int quantity) {
        int result = pricingRepository.updateQuantity(id, quantity);
    }

//    @Override
//    public void createPricing(Pricing pricing) {
//        pricingRepository.createPricing(pricing.getPricingName(),
//                pricing.getPricingCode(),
//                pricing.getProduct().getProductId(),
//                pricing.getPrice(),
//                pricing.getSize(),
//                pricing.getQrCode(),
//                pricing.getInventory(),
//                Long.valueOf(pricing.getColor().getColorId()),
//                pricing.getPricingImgUrl());
//    }


}
