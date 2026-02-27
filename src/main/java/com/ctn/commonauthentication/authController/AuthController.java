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
import jakarta.servlet.http.HttpServletRequest;
import com.ctn.commonauthentication.serviceImpl.RateLimitingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RateLimitingService rateLimitingService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody RegisterRequest request) {

        String ipAddress = getClientIp(httpServletRequest);
        if (!rateLimitingService.tryConsume("register:" + ipAddress, 5, 5, 60000)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ErrorResponse("VALIDATION_ERROR",
                            "Too many registration attempts. Please try again later."));
        }

        String traceId = (requestId != null && !requestId.isBlank()) ? requestId : UUID.randomUUID().toString();

        try {
            RegisterResponse response = authService.register(request);

            if ("ALREADY_EXISTS".equals(response.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponseWithTrace(
                                "VALIDATION_ERROR",
                                "User with this email already exists.",
                                traceId));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseWithTrace(
                            "VALIDATION_ERROR",
                            e.getMessage(),
                            traceId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpServletRequest,
            @Valid @RequestBody LoginRequest loginRequest) {
        String ipAddress = getClientIp(httpServletRequest);
        if (!rateLimitingService.tryConsume("login:" + ipAddress, 5, 5, 60000)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new ErrorResponse("VALIDATION_ERROR", "Too many login attempts. Please try again later."));
        }

        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("VALIDATION_ERROR", "Invalid email or password."));
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
                        .body(new ErrorResponse("VALIDATION_ERROR", "Refresh token is invalid or expired."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("VALIDATION_ERROR", "Authentication required."));
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
                        .body(new ErrorResponse("VALIDATION_ERROR", "Authentication required."));
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
                        .body(new ErrorResponse("VALIDATION_ERROR", "User with this email was not found."));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(
            @RequestHeader(value = "X-Request-ID", required = false) String requestId,
            @Valid @RequestBody PasswordResetExecuteRequest request) {
        String traceId = (requestId != null && !requestId.isBlank()) ? requestId : UUID.randomUUID().toString();
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok(Map.of("message", "パスワードの再設定が完了しました。 / Password reset completed successfully."));
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if ("INVALID_TOKEN".equals(msg) || "EXPIRED_TOKEN".equals(msg)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseWithTrace(
                                "VALIDATION_ERROR",
                                "Reset token is invalid or expired.",
                                traceId));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
