package com.codegym.fashionshop.dto;

import com.codegym.fashionshop.entities.Pricing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

/**
 * Represents a warehouse receipt containing details about the receipt, the user, the date,
 * and the list of pricings.
 *
 * <p>This class is used to encapsulate the information related to a warehouse receipt including
 * the receipt ID, username, date, and a list of pricing details.
 *
 * <p>It provides getters and setters for accessing and modifying these fields.
 *
 * <p>Example usage:
 * <pre>
 *     WarehouseReceipt receipt = new WarehouseReceipt();
 *     receipt.setReceiptId("123");
 *     receipt.setUsername("user1");
 *     receipt.setDate(LocalDate.now());
 *     receipt.setPricingList(pricings);
 * </pre>
 *
 * @see Pricing
 * @see LocalDate
 * @see List
 *
 * @version 1.0
 * @since 2023-07-03
 *
 * @author YourName
 */
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
