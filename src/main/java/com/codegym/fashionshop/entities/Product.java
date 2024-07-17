package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @NotBlank(message = "Mã sản phẩm không được để trống!")
    private String productCode;

    @Column(name = "product_name")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    @NotBlank(message = "Tên giá không được để trống!")
    @Pattern(regexp = "^[a-zA-Z0-9\\s_-]+$", message = "Tên sản phẩm không hợp lệ. Chỉ chấp nhận chữ cái, số, dấu cách, gạch ngang và gạch dưới.")
    @Size(min = 3, max = 50, message = "Tên sản phẩm phải có từ 3 đến 50 ký tự.")
    private String productName;

    @Column(name = "product_desc", columnDefinition = "TEXT")
    @NotBlank(message = "Mô tả sản phẩm không được để trống!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "Loại sản phẩm không được để trống!")
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pricing> pricingList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductImage> productImages;

    @Column(name = "enabled")
    private Boolean enabled;
}
