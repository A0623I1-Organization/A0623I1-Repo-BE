package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Pageable pageable);
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO products (product_code, product_name, product_desc, type_id,pricing) " +
//            "VALUES (:productCode, :productName, :description, :typeId)",
//            nativeQuery = true)
//    void createProduct(@Param("productCode") String productCode,
//                       @Param("productName") String productName,
//                       @Param("description") String description,
//                       @Param("typeId") Long typeId);

}
