package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Category;
import com.codegym.fashionshop.entities.ProductType;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.ICategoryService;
import com.codegym.fashionshop.service.product.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/productType")
@CrossOrigin("*")
public class ProductTypeRestController {
    @Autowired
    private IProductTypeService productTypeService;

    @GetMapping
    public ResponseEntity<List<ProductType>> getProductTypeByCategoryName(@RequestParam(name = "categoryName") String categoryName) {
        List<ProductType> productTypes = productTypeService.findProductTypeByCategory_CategoryName(categoryName);
        if (productTypes.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin màu sắc");
        }
        return new ResponseEntity<>(productTypes, HttpStatus.OK);
    }
}
