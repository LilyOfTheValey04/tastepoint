package com.restaurantplatform.auth_service.auth.dto.register;

import com.restaurantplatform.auth_service.user.UserRole;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        LocalDateTime createdAt
) {
}
