package com.codegym.fashionshop.service.impl;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.repository.IRoleRepository;
import com.codegym.fashionshop.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public AppRole findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

    @Override
    public List<AppRole> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public AppRole findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void save(AppRole appRole) {
        roleRepository.save(appRole);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }
}
