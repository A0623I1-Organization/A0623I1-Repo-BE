package com.codegym.fashionshop.dto.request;

import com.codegym.fashionshop.entities.AppRole;
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
public class RegisterRequest {

    private String username;

    private String password;

    private LocalDate dateCreate;

    private String avatar;

    private String fullName;

    private String userCode;

    private Integer gender;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String email;

    private String address;

    private AppRole role;

}