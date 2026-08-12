package com.restaurantplatform.auth_service.auth;

import com.restaurantplatform.auth_service.auth.dto.login.LoginRequest;
import com.restaurantplatform.auth_service.auth.dto.register.RegisterRequest;
import com.restaurantplatform.auth_service.security.JwtService;
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
    private final JwtService jwtService;

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

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtService.generateToken(user.getEmail());
    }

    public long getExpirationTime() {
        return jwtService.getExpirationTime();
    }

}
