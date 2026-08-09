package com.restaurantplatform.auth_service.auth;

import com.restaurantplatform.auth_service.auth.dto.RegisterRequest;
import com.restaurantplatform.auth_service.user.User;
import com.restaurantplatform.auth_service.user.UserRepository;
import com.restaurantplatform.auth_service.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email()))
        {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(UserRole.CLIENT);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
