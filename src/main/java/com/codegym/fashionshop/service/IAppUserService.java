package com.codegym.fashionshop.service;


import com.codegym.fashionshop.entities.AppUser;


public interface IAppUserService extends IGeneralService<AppUser>{
    boolean existsByUsername(String username);
    AppUser findByUsername(String username);
}
