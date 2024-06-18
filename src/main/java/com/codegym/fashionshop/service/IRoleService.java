package com.codegym.fashionshop.service;

import com.codegym.fashionshop.entities.AppRole;

public interface IRoleService extends IGeneralService<AppRole> {
    AppRole findByRoleName(String role);
}
