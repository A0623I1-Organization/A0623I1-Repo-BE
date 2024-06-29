package com.codegym.fashionshop.repository.product;

import com.codegym.fashionshop.entities.Category;
import com.codegym.fashionshop.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductTypeRepository extends JpaRepository<ProductType,Long> {
    List<ProductType> findProductTypeByCategory_CategoryName(String category_categoryName);
    List<ProductType> findProductTypeByCategory_CategoryId(Long category_categoryId);
}
