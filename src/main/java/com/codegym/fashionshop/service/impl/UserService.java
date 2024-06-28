package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.repository.IRoleRepository;
import com.codegym.fashionshop.repository.IUserRepository;
import com.codegym.fashionshop.service.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService implements IAppUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AuthenticationResponse searchAllByUsernameOrUserCodeOrRoleName(String searchContent, Pageable pageable) {
        Page<AppUser> users = userRepository.searchAppUserByUsernameOrUserCodeOrRoleName(searchContent, pageable);
        if (users.getTotalElements() == 0) {
            return AuthenticationResponse.builder()
                    .statusCode(404)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Lấy danh sách nhân viên thành công!")
                .users(users)
                .build();
    }

    @Override
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(AppUser appUser) {
        userRepository.save(appUser);
    }

    @Override
    public AuthenticationResponse save(AppUserRequest appUserRequest) {
        var user = AppUser.builder()
                .username(appUserRequest.getUsername())
                .encryptedPassword(passwordEncoder.encode(appUserRequest.getNewPassword()))
                .fullName(appUserRequest.getFullName())
                .userCode(appUserRequest.getUserCode())
                .dateOfBirth(appUserRequest.getDateOfBirth())
                .phoneNumber(appUserRequest.getPhoneNumber())
                .email(appUserRequest.getEmail())
                .address(appUserRequest.getAddress())
                .dateCreate(LocalDate.now())
                .avatar(appUserRequest.getAvatar())
                .role(appUserRequest.getRole())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Thêm mới thành công!")
                .build();
    }

    public AuthenticationResponse updateUser(Long userId, AppUserRequest appUserRequest) {
        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        AppUser appUser = user.get();
        appUser.setUserId(appUserRequest.getUserId());
        appUser.setUsername(appUserRequest.getUsername());
        appUser.setFullName(appUserRequest.getFullName());
        appUser.setUserCode(appUserRequest.getUserCode());
        appUser.setDateOfBirth(appUserRequest.getDateOfBirth());
        appUser.setPhoneNumber(appUserRequest.getPhoneNumber());
        appUser.setEmail(appUserRequest.getEmail());
        appUser.setAddress(appUserRequest.getAddress());
        appUser.setDateCreate(appUserRequest.getDateCreate());
        appUser.setAvatar(appUserRequest.getAvatar());
        appUser.setRole(appUserRequest.getRole());
        userRepository.save(appUser);
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Cập nhật thành công!")
                .build();
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public AuthenticationResponse findUserUpdate(Long id){
        Optional<AppUser> appUser = userRepository.findById(id);
        List<AppRole> roles = roleRepository.findAll();
        if (appUser.isEmpty()) {
            return AuthenticationResponse.builder()
                    .statusCode(400)
                    .message("Không tìm thấy kết quả!")
                    .build();
        }
        AppUser updatedAppUser = appUser.get();
        return AuthenticationResponse.builder()
                .statusCode(200)
                .message("Đã tìm thấy kết quả!")
                .userId(id)
                .username(updatedAppUser.getUsername())
                .userCode(updatedAppUser.getUserCode())
                .fullName(updatedAppUser.getFullName())
                .gender(updatedAppUser.getGender())
                .dateOfBirth(updatedAppUser.getDateOfBirth())
                .dateOfBirth(updatedAppUser.getDateCreate())
                .email(updatedAppUser.getEmail())
                .phoneNumber(updatedAppUser.getPhoneNumber())
                .address(updatedAppUser.getAddress())
                .role(updatedAppUser.getRole())
                .roles(roles)
                .build();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
