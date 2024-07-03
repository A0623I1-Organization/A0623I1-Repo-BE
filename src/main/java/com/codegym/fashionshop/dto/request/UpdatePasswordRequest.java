package com.codegym.fashionshop.dto.request;

import org.springframework.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

/**
 * Represents a request to update an existing user in the system.
 * This includes various user details such as username, password, contact information, and role.
 * <p>
 * Author: KhangDV
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest implements Validator {
    @NotBlank(message = "Tên đăng nhập không được để trống!")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống!")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu không được để trống!")
    private String newPassword;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống!")
    private String confirmPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UpdatePasswordRequest updatePasswordRequest = (UpdatePasswordRequest) target;
        String newPassword = updatePasswordRequest.getNewPassword();
        String confirmPassword = updatePasswordRequest.getConfirmPassword();
        if (!confirmPassword.equals(newPassword)){
            errors.rejectValue("confirmPassword","", "Mật khẩu không trùng khớp!!");
        }
    }
}
