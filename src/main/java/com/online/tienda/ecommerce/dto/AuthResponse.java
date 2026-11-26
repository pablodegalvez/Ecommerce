package com.online.tienda.ecommerce.dto;

import java.util.List;

public record AuthResponse(
        String token,
        String type,
        String email,
        String username,
        List<String> roles
) {
    public AuthResponse(String token, String email, String username, List<String> roles) {
        this(token, "Bearer", email, username, roles);
    }
}
