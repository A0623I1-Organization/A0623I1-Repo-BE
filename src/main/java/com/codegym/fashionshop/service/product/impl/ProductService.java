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
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void createProduct(Product product) {
        productRepository.createProduct(
                product.getProductCode(),
                product.getProductName(),
                product.getDescription(),
                product.getProductType().getTypeId()
        );
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

    @Override
    public Page<Product> searchAndSortProducts(String keyword, Pageable pageable) {
        return productRepository.findByProductCodeContainingIgnoreCaseOrProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrProductType_TypeNameContainingIgnoreCase(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }
}
