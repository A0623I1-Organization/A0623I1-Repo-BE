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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "date_register")
    private LocalDate dateRegister;

    @Column(name = "accumulated_points")
    private int accumulatedPoints;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CustomerType customerType;

    @ManyToOne
    @JoinColumn(name = "promotions_for_customer")
    private PromotionForCustomer promotionForCustomer;

    @ManyToOne
    @JoinColumn(name = "gift")
    private Gift gift;
}
