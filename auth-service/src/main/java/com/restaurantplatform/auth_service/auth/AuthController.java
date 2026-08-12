package com.restaurantplatform.auth_service.auth;

import com.restaurantplatform.auth_service.auth.dto.login.LoginRequest;
import com.restaurantplatform.auth_service.auth.dto.login.LoginResponse;
import com.restaurantplatform.auth_service.auth.dto.register.RegisterRequest;
import com.restaurantplatform.auth_service.auth.dto.register.RegisterResponse;
import com.restaurantplatform.auth_service.security.JwtService;
import com.restaurantplatform.auth_service.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<User> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = authService.register(request);

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getCreatedAt()

        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        LoginResponse response = new LoginResponse(
                token,
                authService.getExpirationTime()
        );

        return ResponseEntity.ok(response);
    }

}
