package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.UpdatePasswordRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.exceptions.UserIsExistException;
import com.codegym.fashionshop.service.authenticate.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    /**
     * Authenticates a user with the provided login credentials.
     *
     * @param request The authentication request containing login credentials.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PostMapping("/api/auth/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authentication(request));
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     * @throws RuntimeException if an error occurs while retrieving user information.
     */
    @GetMapping("/admin-user/get-profile")
    public ResponseEntity<?> getMyProfile() throws RuntimeException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = service.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates the information of a user.
     *
     * @param userId     The ID of the user to be updated.
     * @param updatePasswordRequest The updated user information.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse}.
     */
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<?> updateUser(@Validated @PathVariable Long userId
            , @RequestBody UpdatePasswordRequest updatePasswordRequest
            , BindingResult bindingResult) throws UserIsExistException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthenticationResponse response = service.updatePassword(updatePasswordRequest, username);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}