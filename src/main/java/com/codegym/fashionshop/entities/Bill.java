package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private boolean payment;

    @Column(name = "qr_code")
    private String qrCode;

}