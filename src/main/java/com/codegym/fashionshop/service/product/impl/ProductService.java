package com.codegym.fashionshop.service.product.impl;

import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.Product;
import com.codegym.fashionshop.entities.ProductImage;
import com.codegym.fashionshop.repository.product.IProductRepository;
import com.codegym.fashionshop.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public boolean isProductCodeUnique(String productCode) {
        return !productRepository.existsByProductCode(productCode);
    }

//    @Override
//    public void saveProductWithDetail(Product product, List<Pricing> pricingList, List<ProductImage> productImages) {
//        for (Pricing pricing:pricingList) {
//            pricing.setProduct(product);
//        }
//        for (ProductImage productImage:productImages) {
//            productImage.setProduct(product);
//        }
//        productRepository.save(product);
//    }

//    public void createProduct(Product product) {
//        productRepository.createProduct(product.getProductCode(),
//                product.getProductName(),
//                product.getDescription(),
//                product.getProductType().getTypeId());
//    }
}
