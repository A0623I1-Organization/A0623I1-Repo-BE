package com.codegym.fashionshop.controller;

import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.service.authenticate.impl.RoleService;
import com.codegym.fashionshop.service.authenticate.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing users and roles.
 * This class provides endpoints for user management including retrieving, creating, updating users and fetching roles.
 * <p>
 * Author: KhangDV
 */
@RestController
@CrossOrigin("*")
@RequestMapping("api/auth/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Retrieves all users with optional search and pagination.
     *
     * @param page          The page number for pagination (default is 0).
     * @param searchContent The search content to filter users by username, user code, or role name (default is empty).
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the list of users.
     */
    @GetMapping()
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "searchContent", defaultValue = "") String searchContent) {
        if (page < 0) {
            page = 0;
        }
        AuthenticationResponse response = userService.searchAllByUsernameOrUserCodeOrRoleName(searchContent, PageRequest.of(page, 10));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Retrieves all roles.
     *
     * @return A {@link ResponseEntity} containing the list of roles.
     */
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        System.out.println("call");
        return ResponseEntity.ok(roleService.findAll());
    }

    /**
     * Retrieves a user for update by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the user details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserUpdate(@PathVariable(name = "id") Long id) {
        AuthenticationResponse response = userService.findUserUpdate(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Creates a new user with the provided details.
     *
     * @param appUserRequest The request containing user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the creation.
     */
    @PostMapping()
    public ResponseEntity<?> createUser(@Validated @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        AuthenticationResponse response = userService.save(appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Updates an existing user with the provided details.
     *
     * @param id             The ID of the user to be updated.
     * @param appUserRequest The request containing updated user details.
     * @return A {@link ResponseEntity} containing the {@link AuthenticationResponse} with the status of the update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Validated @PathVariable Long id, @RequestBody AppUserRequest appUserRequest,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        AuthenticationResponse response = userService.updateUser(id, appUserRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
