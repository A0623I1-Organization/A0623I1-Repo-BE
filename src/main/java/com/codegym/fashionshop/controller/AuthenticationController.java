package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.RegisterRequest;
import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.exceptions.UserIsExistException;
import com.codegym.fashionshop.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse token = authenticationService.register(request);
            return ResponseEntity.ok(token);
        } catch (UserIsExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authentication(request));
    }
//
//    @GetMapping("/admin-user/get-profile")
//    public ResponseEntity<?> getMyProfile(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        System.out.println(username);
//        AuthenticationResponse response = authenticationService.getMyInfo(username);
//        return  ResponseEntity.status(response.getStatusCode()).body(response);
//    }
//
//    @PutMapping("/admin/update/{userId}")
//    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody AppUserRequest updateUser){
////        return ResponseEntity.ok(service.updateUser(userId, updateUser));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        AuthenticationResponse response = service.updatePassword(updateUser, username);
//        return ResponseEntity.status(response.getStatusCode()).body(response);
//    }

}