package com.ctn.commonauthentication.authController;

import com.ctn.commonauthentication.LoginResponse;
import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid credentials: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            RefreshTokenResponse response = authService.refreshToken(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest request) {
        try {
            ApiResponse response = authService.logout(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);
            if (response.isAlreadyExisted()) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserResponse response = authService.getUserByEmail(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest request) {
        try {
            ApiResponse response = authService.resetPassword(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/test/sales")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<?> salesTest() {
        return ResponseEntity.ok(new ApiResponse(true, "Sales endpoint accessed successfully"));
    }

    @GetMapping("/test/purchase")
    @PreAuthorize("hasAnyRole('PURCHASE', 'ADMIN')")
    public ResponseEntity<?> purchaseTest() {
        return ResponseEntity.ok(new ApiResponse(true, "Purchase endpoint accessed successfully"));
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminTest() {
        return ResponseEntity.ok(new ApiResponse(true, "Admin endpoint accessed successfully"));
    }
}
