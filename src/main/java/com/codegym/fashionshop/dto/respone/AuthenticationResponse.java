package com.codegym.fashionshop.dto.respone;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
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
public class AuthenticationResponse {
    private int statusCode;

    private String error;

    private String message;

    private String token;

    private String username;

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

    private List<AppUser> users;
}