package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.dto.UserInforUserDetails;
import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.RegisterRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.dto.respone.UserInformation;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.exceptions.UserIsExistException;
import com.codegym.fashionshop.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private IUserRepository userRepository;

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
        UserInforUserDetails userDetails = new UserInforUserDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
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


    public AuthenticationResponse getMyInfo(String username){
        try {
            AppUser user = userRepository.findByUsername(username);
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .message("Successfully!")
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
                    .build();
        }catch (Exception e){
            return AuthenticationResponse.builder()
                    .statusCode(500)
                    .message("Error occurred while getting user info: " + e.getMessage()).build();
        }
    }
}