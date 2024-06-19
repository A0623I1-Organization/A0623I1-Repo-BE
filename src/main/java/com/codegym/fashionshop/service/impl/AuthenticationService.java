package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.dto.UserInforUserDetails;
import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.RegisterRequest;
import com.codegym.fashionshop.dto.request.UpdateUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.dto.respone.UserInformation;
import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.exceptions.UserIsExistException;
import com.codegym.fashionshop.repository.IRoleRepository;
import com.codegym.fashionshop.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var checkUser = userRepository.findByUsername(request.getUsername());
        if (checkUser != null) {
            throw new UserIsExistException();
        }
        var user = AppUser.builder()
                .username(request.getUsername())
                .encryptedPassword(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .userCode(request.getUserCode())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .dateCreate(LocalDate.now())
                .avatar(request.getAvatar())
                .role(request.getRole())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("User registered successfully")
                .build();
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsername(request.getUsername());
            UserInforUserDetails userDetails = new UserInforUserDetails(user);
            var jwtToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .token(jwtToken)
                    .message("Successfully Logged In!!!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .statusCode(500).build();
        }
    }


    public AuthenticationResponse getMyInfo(String username) {
        try {
            AppUser user = userRepository.findByUsername(username);
            List<AppRole> roles = roleRepository.findAll();
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .message("Successfully!")
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .userCode(user.getUserCode())
                    .dateCreate(user.getDateCreate())
                    .dateOfBirth(user.getDateOfBirth())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .fullName(user.getFullName())
                    .gender(user.getGender())
                    .avatar(user.getAvatar())
                    .address(user.getAddress())
                    .roles(roles)
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(500)
                    .message("Error occurred while getting user info: " + e.getMessage()).build();
        }
    }

    public AuthenticationResponse updateUser(Long userId, UpdateUserRequest updatedUser) {
        try {
            Optional<AppUser> user = userRepository.findById(userId);
            List<AppRole> roles = roleRepository.findAll();
            if (user.isPresent()) {
                AppUser appUser = user.get();
                appUser.setAvatar(updatedUser.getAvatar());
                appUser.setFullName(updatedUser.getFullName());
                appUser.setGender(updatedUser.getGender());
                appUser.setEmail(updatedUser.getEmail());
                appUser.setPhoneNumber(updatedUser.getPhoneNumber());
                appUser.setRole(updatedUser.getRole());
                appUser.setDateCreate(updatedUser.getDateCreate());
                appUser.setDateOfBirth(updatedUser.getDateOfBirth());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    appUser.setEncryptedPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                AppUser appUser1 = userRepository.save(appUser);
                return AuthenticationResponse.builder()
                        .statusCode(200)
                        .message("User updated successfully")
                        .userId(appUser1.getUserId())
                        .username(appUser1.getUsername())
                        .userCode(appUser1.getUserCode())
                        .dateCreate(appUser1.getDateCreate())
                        .dateOfBirth(appUser1.getDateOfBirth())
                        .email(appUser1.getEmail())
                        .phoneNumber(appUser1.getPhoneNumber())
                        .role(appUser1.getRole())
                        .fullName(appUser1.getFullName())
                        .gender(appUser1.getGender())
                        .avatar(appUser1.getAvatar())
                        .address(appUser1.getAddress())
                        .roles(roles)
                        .build();
            } else {
                return AuthenticationResponse.builder()
                        .statusCode(404)
                        .message("User not found for update")
                        .build();
            }
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .statusCode(500)
                    .message("Error occurred while updating user: " + e.getMessage())
                    .build();
        }
    }
}