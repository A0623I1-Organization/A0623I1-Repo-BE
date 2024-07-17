package com.codegym.fashionshop.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "bill_code", nullable = false, unique = true)
    @NotBlank(message = "Mã hóa đơn không được để trống!")
    private String billCode;

    @Column(name = "date_create", columnDefinition = "DATE", nullable = false)
    @NotNull(message = "Ngày tạo không được để trống!")
    private LocalDate dateCreate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Người dùng không được để trống!")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Khách hàng không được để trống!")
    private Customer customer;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillItem> billItemList;

    @Column(name = "promotion_code")
    private String promotionCode;
}
