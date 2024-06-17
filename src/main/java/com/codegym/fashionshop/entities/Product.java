package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_code", unique = true)
    @Pattern(regexp = "^[A-Z]{2}[0-9A-Z]{4}$", message = "Vui lòng nhập đúng định dạng, mã sản phẩm chỉ phải bắt đầu bằng 2 chữ cái in hoa và kết thúc bằng 4 ký tự chỉ bao gồm số và chữ!")
    private String productCode;

    @Column(name = "product_name")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    private String productName;

    @Column(name = "product_desc",columnDefinition = "TEXT")
    @NotNull(message = "Mô tả sản phẩm không được để trống!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType productType;

}
