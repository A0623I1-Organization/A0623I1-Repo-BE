package com.codegym.fashionshop.dto.respone;

import com.codegym.fashionshop.entities.Pricing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceipt {
    private String receiptId;
    private String username;
    private LocalDate date;
    private List<Pricing> pricingList;
}
