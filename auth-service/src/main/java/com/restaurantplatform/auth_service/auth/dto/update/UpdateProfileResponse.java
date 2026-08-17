package com.restaurantplatform.auth_service.auth.dto.update;

import com.restaurantplatform.auth_service.user.UserRole;

import java.time.LocalDateTime;

public record UpdateProfileResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        UserRole role,
        LocalDateTime createdAt) {
}
