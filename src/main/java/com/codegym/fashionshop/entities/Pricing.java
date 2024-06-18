package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pricings")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pricing_id")
    private Long pricingId;

    @Column(name = "price_name")
    private String pricingName;

    @Column(name = "pricing_code")
    private String pricingCode;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    @Min(value = 0, message = "Giá thành phải lớn hơn hoặc bằng 0!")
    private Double price;

    private String size;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "inventory")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0!")
    private Integer inventory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "pricing_image")
    private String pricingImgUrl;
}
