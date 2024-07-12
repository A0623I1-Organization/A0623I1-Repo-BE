package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.UpdatePasswordRequest;
import com.codegym.fashionshop.dto.request.UpdateUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.exceptions.UserIsExistException;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user authentication and profile.
 * This class provides endpoints for user authentication, profile retrieval, and user update.
 * <p>
 * Author: KhangDV
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authentication(request));
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     * @throws RuntimeException if an error occurs while retrieving user information.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER')")
    @GetMapping("/get-profile")
    public ResponseEntity<?> getMyProfile() throws RuntimeException{
        System.out.println("call");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = authenticationService.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates the information of a user.
     *
     * @param updatePasswordRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER')")
    @PutMapping("/update-password")
    public ResponseEntity<?> updateUser(@Validated @RequestBody UpdatePasswordRequest updatePasswordRequest
            , BindingResult bindingResult){
        System.out.println("call update");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        AuthenticationResponse response = authenticationService.updatePassword(updatePasswordRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates avatar and background image of a user.
     *
     * @param updateUserRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PreAuthorize("hasAnyRole('ROLE_SALESMAN', 'ROLE_WAREHOUSE', 'ROLE_MANAGER')")
    @PatchMapping("/update-image")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        AuthenticationResponse response = authenticationService.updateAvatarAndBackgroundImage(updateUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}