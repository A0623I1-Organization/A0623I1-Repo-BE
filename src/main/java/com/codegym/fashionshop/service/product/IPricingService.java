package com.codegym.fashionshop.service.product;

import com.codegym.fashionshop.dto.respone.WarehouseReceipt;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.repository.product.IPricingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IPricingService  {
    List<Pricing> findAllPricing();
    Page<Pricing> findAllPricing(Pageable pageable);
    Page<Pricing> findAllByProduct_ProductId(Long productId,Pageable pageable);

    void createPricing(Pricing pricing);

    void updatePricingQuantity(WarehouseReceipt warehouseReceipt);
}
