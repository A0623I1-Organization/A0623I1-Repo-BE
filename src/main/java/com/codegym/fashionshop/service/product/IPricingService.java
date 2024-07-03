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

    /**
     * Updates the pricing quantities based on the provided warehouse receipt.
     *
     * <p>This method updates the inventory with the user ID and date from the warehouse receipt,
     * retrieves the last inserted inventory ID, and updates the quantity and inventory ID for each
     * pricing in the warehouse receipt.
     *
     * @param warehouseReceipt The warehouse receipt containing the pricing list and other details.
     */
    void updatePricingQuantity(WarehouseReceipt warehouseReceipt);
    boolean isPricingCodeUnique(String pricingCode);
    Pricing findByPricingCode(String pricingCode);

}
