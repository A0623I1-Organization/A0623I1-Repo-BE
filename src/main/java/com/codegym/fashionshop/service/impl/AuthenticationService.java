package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.dto.UserInforUserDetails;
import com.codegym.fashionshop.dto.request.AuthenticationRequest;
import com.codegym.fashionshop.dto.request.RegisterRequest;
import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
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
                .message("Đăng ký thành công!")
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
            System.out.println("Call-------");
            var user = userRepository.findByUsername(request.getUsername());
            UserInforUserDetails userDetails = new UserInforUserDetails(user);
            var jwtToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .token(jwtToken)
                    .fullName(user.getFullName())
                    .message("Đăng nhập thành công!!!")
                    .build();
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .message(e.getMessage())
                    .statusCode(500).build();
        }
    }

    public AuthenticationResponse getMyInfo(String username) {
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            return AuthenticationResponse.builder()
                    .statusCode(200)
                    .message("Thành công!")
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
                    .build();
        } else {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();
        }
    }

    public AuthenticationResponse updateUser(Long userId, AppUserRequest updatedUser) {
        Optional<AppUser> user = userRepository.findById(userId);
        List<AppRole> roles = roleRepository.findAll();
        if (user.isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();

        }
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
        if (updatedUser.getNewPassword() != null && !updatedUser.getNewPassword().isEmpty()) {
            // Encode the password and update it
            appUser.setEncryptedPassword(passwordEncoder.encode(updatedUser.getNewPassword()));
        }

        AppUser appUser1 = userRepository.save(appUser);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật người dùng thành công")
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
                .build();
    }

    public AuthenticationResponse updatePassword(AppUserRequest updatedUser, String username) {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Người dùng không được tìm thấy!")
                    .build();

        }
        if (updatedUser.getOldPassword() == null && updatedUser.getOldPassword().isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Vui lòng nhập đúng mật khẩu!").build();
        }
        if (updatedUser.getNewPassword() == null && updatedUser.getNewPassword().isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu mới không được để trống")
                    .build();

        }
        if (!updatedUser.getConfirmPassword().equals(updatedUser.getNewPassword())) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Mật khẩu không trùng khớp!").build();
        }
        user.setEncryptedPassword(passwordEncoder.encode(updatedUser.getNewPassword()));
        AppUser appUser = userRepository.save(user);
        UserInforUserDetails userDetails = new UserInforUserDetails(appUser);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật mật khẩu thành công!")
                .userId(appUser.getUserId())
                .username(appUser.getUsername())
                .userCode(appUser.getUserCode())
                .dateCreate(appUser.getDateCreate())
                .dateOfBirth(appUser.getDateOfBirth())
                .email(appUser.getEmail())
                .phoneNumber(appUser.getPhoneNumber())
                .role(appUser.getRole())
                .fullName(appUser.getFullName())
                .gender(appUser.getGender())
                .avatar(appUser.getAvatar())
                .address(appUser.getAddress())
                .token(jwtToken)
                .build();
    }
}
