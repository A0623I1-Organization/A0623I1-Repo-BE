package com.codegym.fashionshop.service.product;


import com.codegym.fashionshop.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IProductService {
    void createProduct(Product product);

    List<Product> findAllProduct();
    Page<Product> findAllProduct(Pageable pageable);

    boolean isProductCodeUnique(String productCode);

//    void saveProductWithDetail(Product product, List<Pricing> pricingList, List<ProductImage> productImages);
Page<Product> searchAndSortProducts(String keyword, Pageable pageable);
void save(Product product);
}