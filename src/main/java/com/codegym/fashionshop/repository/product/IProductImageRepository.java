package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage,Long> {
}
