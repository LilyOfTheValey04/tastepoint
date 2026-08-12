package com.restaurantplatform.auth_service.auth.dto.login;

public record LoginResponse(
        String token,
        long expiresIn
) {
}
