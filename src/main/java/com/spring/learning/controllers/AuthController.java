package com.spring.learning.controllers;

import com.spring.learning.dto.shared.ApiResponseDto;
import com.spring.learning.dto.auth.AuthResponseDto;
import com.spring.learning.dto.auth.LoginDto;
import com.spring.learning.dto.auth.RegisterDto;
import com.spring.learning.services.auth.AuthService;
import com.spring.learning.services.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("Attempting to log in user: {}", loginDto.getUsername());

        try {
            AuthResponseDto authResponse = authService.authenticateUser(loginDto);
            log.info("User logged in successfully: {}", loginDto.getUsername());
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            log.warn("Login failed for user {}: Invalid credentials.", loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto("Username/password is incorrect", false));
        } catch (Exception e) {
            log.error("Unexpected error during login for user {}: {}", loginDto.getUsername(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto("Something went wrong", false));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {
        log.info("Attempting to register a user: {}", registerDto.getUsername());

        boolean isRegistered = userService.registerUser(registerDto);
        if (!isRegistered) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("Username is already taken", false));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto("User registered successfully", true));
    }
}

