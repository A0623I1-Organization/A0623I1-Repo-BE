package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Column(name = "pricing_name")
    private String pricingName;

    @Column(name = "pricing_code")
    private String pricingCode;

    //    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    @JsonIgnore // Để tránh vòng lặp khi serialize JSON
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @Column(name = "price")
    @Min(value = 0, message = "Giá thành phải lớn hơn hoặc bằng 0!")
    private Double price;

    private String size;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "quantity", columnDefinition = "integer default 0")
    @Min(value = 0, message = "Số lượng tồn kho phải lớn hơn hoặc bằng 0!")
    private Integer quantity = 0;

    @ManyToOne()
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "pricing_image")
    private String pricingImgUrl;

<<<<<<< HEAD

=======
>>>>>>> 561cc94c6d22e90764c92d5a5e88f803de01fcd9
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

}
