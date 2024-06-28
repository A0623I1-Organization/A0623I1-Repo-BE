package com.codegym.fashionshop.service;


import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.AppUser;
import org.springframework.data.domain.Pageable;


public interface IAppUserService extends IGeneralService<AppUser>{
    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    AuthenticationResponse searchAllByUsernameOrUserCodeOrRoleName(String searchContent, Pageable pageable);

    AuthenticationResponse save(AppUserRequest appUserRequest);
}
