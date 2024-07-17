package com.codegym.fashionshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notif_id")
    private Long notifId;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NotNull(message = "Không được để trống")
    @NotBlank(message = "Không được để trống")
    @Size(min = 2, message = "Tiêu đề phải hơn 2 ký tự.")
    private String topic;

    @NotNull(message = "Không được để trống")
    @NotBlank(message = "Không được để trống")
    private String content;

}

