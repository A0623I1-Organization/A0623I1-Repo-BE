package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Pricing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IPricingRepository extends JpaRepository<Pricing,Long> {
    @Query(value = "SELECT p.*, pr.product_name, c.color_name FROM pricings p " +
            "JOIN products pr ON p.product_id = pr.product_id " +
            "JOIN colories c ON p.color_id = c.color_id", nativeQuery = true)
    List<Pricing> findAllPricings();


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO pricings (pricing_name, pricing_code, product_id, price, size, qr_code, inventory, color_id, pricing_image) " +
            "VALUES (:pricingName, :pricingCode, :productId, :price, :size, :qrCode, :inventory, :colorId, :pricingImgUrl)",
            nativeQuery = true)
    void createPricing(@Param("pricingName") String pricingName,
                       @Param("pricingCode") String pricingCode,
                       @Param("productId") Long productId,
                       @Param("price") Double price,
                       @Param("size") String size,
                       @Param("qrCode") String qrCode,
                       @Param("inventory") Integer inventory,
                       @Param("colorId") Long colorId,
                       @Param("pricingImgUrl") String pricingImgUrl);
  
    Page<Pricing> findAll(Pageable pageable);
  
    Page<Pricing> findAllByProduct_ProductId(Long productId,Pageable pageable);
  
    @Query(value = "UPDATE pricings set quantity = :quantity where pricing_id = : id", nativeQuery = true)
    int updateQuantity(Long id, int quantity);

}
