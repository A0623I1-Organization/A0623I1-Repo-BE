package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The Customer class represents a customer in the system.
 * <p>
 * This class contains attributes and methods necessary to manage a customer's information.
 * Each customer has a unique code, name, date of birth, gender, email, phone number, address,
 * registration date, accumulated points, and customer type.
 * <p>
 * The attributes are validated to ensure the input data is correct.
 * <p>
 * Author: [QuyLV]
 */
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

    @NotBlank(message = "Mã khách hàng không được để trống !")
    @Pattern(regexp = "^KH-\\d{3}$", message = "Mã khách hàng phải có định dạng KH-XXX")
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @NotBlank(message = "Tên khách hàng không được để trống !")
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Past(message = "Ngày phải là ngày quá khứ !")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull(message = "Giới tính không được để trống !")
    @Column(name = "gender")
    private Integer gender;

    @Email(message = "Email phải hợp lệ")
    @NotBlank(message = "Email không được để trống !")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải 10 số ")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ không được để trống !")
    @Column(name = "address")
    private String address;

    @Column(name = "date_register")
    private LocalDate dateRegister;

    @Min(value = 0, message = "Số tối thiểu phải bằng 0")
    @Column(name = "accumulated_points")
    private int accumulatedPoints;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CustomerType customerType;
}
