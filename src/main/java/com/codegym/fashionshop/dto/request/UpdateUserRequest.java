package com.codegym.fashionshop.dto.request;

import com.codegym.fashionshop.entities.AppRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Long userId;

    private String username;

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

    private String userCode;

    private String avatar;

    private String fullName;

    private LocalDate dateOfBirth;

    private Integer gender;

    private String phoneNumber;

    private String email;

    private String address;

    private AppRole role;

    private LocalDate dateCreate;
}
