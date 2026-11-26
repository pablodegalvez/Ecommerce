package com.online.tienda.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        @NotBlank(message = "Username is required")
        @Size(max = 30, message = "Username has to be 30 character or shorter")
        String username
) {
}
