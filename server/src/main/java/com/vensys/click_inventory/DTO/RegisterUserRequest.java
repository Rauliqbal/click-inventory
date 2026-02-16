package com.vensys.click_inventory.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    @NotBlank(message = "username is required")
    @Size(max = 100)
    private String username;

    @NotBlank(message = "fullname is required")
    @Size(max = 100)
    private String fullname;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 255, message = "password must be at least 8 characters")
    private String password;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "role is required")
    private String role;
}
