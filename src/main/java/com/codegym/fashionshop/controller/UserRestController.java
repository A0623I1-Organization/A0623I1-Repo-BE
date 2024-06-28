package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.service.impl.RoleService;
import com.codegym.fashionshop.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "searchContent", defaultValue = "") String searchContent) {
        if (page < 0) {
            page = 0;
        }
        AuthenticationResponse response = userService.searchAllByUsernameOrUserCodeOrRoleName(searchContent, PageRequest.of(page, 10));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserUpdate(@PathVariable(name = "id") Long id) {
        AuthenticationResponse response = userService.findUserUpdate(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody AppUserRequest appUserRequest) {
        AuthenticationResponse response = userService.save(appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody AppUserRequest appUserRequest) {
        AuthenticationResponse response = userService.updateUser(id, appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
