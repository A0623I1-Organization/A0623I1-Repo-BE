package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "promotion_name")
    @NotBlank(message = "Tên khuyến mãi không được để trống!")
    private String promotionName;

    @Column(name = "discount")
    @Min(value = 0, message = "tỉ lệ không được nhỏ hơn 0 và phải từ 0 đến 1!")
    @Max(value = 1, message = "tỉ lệ không được lớn hơn 1 và phải từ 0 đến 1!")
    private Double discount;

    @Column(name = "gift")
    private String gift;

    @Column(name = "required_bill")
    private Double requiredBill;

    @Column(name = "accumulated_points")
    private int accumulatedPoints;
}