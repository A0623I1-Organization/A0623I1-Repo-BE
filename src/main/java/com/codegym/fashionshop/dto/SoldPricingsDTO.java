package com.codegym.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoldPricingsDTO {
    private String pricingCode;
    private String pricingName;
    private int totalQuantity;
    private double price;
}
