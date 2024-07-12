package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Product;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for managing products.
 * Includes CRUD operations and custom queries for products.
 * Author: HoaNTT
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable pagination information
     * @return a page of products
     */
    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    /**
     * Checks if a product with the given product code exists.
     *
     * @param productCode the product code to check
     * @return true if a product with the given code exists, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.productCode = :productCode")
    boolean existsByProductCode(@Param("productCode") String productCode);

    /**
     * Creates a new product.
     *
     * @param productCode the product code
     * @param productName the product name
     * @param description the product description
     * @param typeId      the ID of the product type
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO products (product_code, product_name, product_desc, type_id) " +
            "VALUES (:productCode, :productName, :description, :typeId)",
            nativeQuery = true)
    void createProduct(@Param("productCode") String productCode,
                       @Param("productName") String productName,
                       @Param("description") String description,
                       @Param("typeId") Long typeId);

    /**
     * Searches for products based on various criteria with pagination.
     *
     * @param productCode     product code to search
     * @param productName     product name to search
     * @param description     product description to search
     * @param productTypeName product type name to search
     * @param pageable        pagination information
     * @return a page of products matching the search criteria
     */
    Page<Product> findByProductCodeContainingIgnoreCaseOrProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrProductType_TypeNameContainingIgnoreCase(
            String productCode,
            String productName,
            String description,
            String productTypeName,
            Pageable pageable
    );
}