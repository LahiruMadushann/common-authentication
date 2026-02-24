package com.ctn.commonauthentication.authController;

import com.ctn.commonauthentication.dto.LoginResponse;
import com.ctn.commonauthentication.authService.AuthService;
import com.ctn.commonauthentication.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @Valid @RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = authService.register(request);

            if ("ALREADY_EXISTS".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponseWithTrace(
                                "DUPLICATE_EMAIL",
                                "User with this email already exists.",
                                UUID.randomUUID().toString()));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseWithTrace(
                            "VALIDATION_ERROR",
                            e.getMessage(),
                            UUID.randomUUID().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("UNAUTHORIZED", "Invalid email or password."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            LoginResponse response = authService.refreshToken(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message != null && (message.contains("Invalid") || message.contains("expired")
                    || message.contains("revoked") || message.contains("not found"))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("UNAUTHORIZED", "Refresh token is invalid or expired."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("UNAUTHORIZED", "Authentication required."));
            }
            authService.logout(userDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("UNAUTHORIZED", "Authentication required."));
            }
            UserProfileResponse profile = authService.getUserProfile(userDetails.getUsername());
            return ResponseEntity.ok(profile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordForgotRequest request) {
        try {
            authService.forgotPassword(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("VALIDATION_ERROR", e.getMessage()));
        } catch (RuntimeException e) {
            if ("USER_NOT_FOUND".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("USER_NOT_FOUND", "User with this email was not found."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @Valid @RequestBody PasswordResetExecuteRequest request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok(Map.of("message", "パスワードの再設定が完了しました。 / Password reset completed successfully."));
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if ("INVALID_TOKEN".equals(msg) || "EXPIRED_TOKEN".equals(msg)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseWithTrace(
                                "INVALID_TOKEN",
                                "Reset token is invalid or expired.",
                                UUID.randomUUID().toString()));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
