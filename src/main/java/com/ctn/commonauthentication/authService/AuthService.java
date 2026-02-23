package com.ctn.commonauthentication.authService;

import com.ctn.commonauthentication.LoginResponse;
import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.entity.GlobalUser;
import com.ctn.commonauthentication.entity.UserModel;
import com.ctn.commonauthentication.repository.GlobalUserRepository;
import com.ctn.commonauthentication.repository.UserModelRepository;
import com.ctn.commonauthentication.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GlobalUserRepository userRepository;
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        GlobalUser user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtil.generateAccessToken(userDetails, user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setToken(refreshToken);
        user.setCreatedAt(LocalDateTime.now());
        user.setExpiresAt(LocalDateTime.now().plusDays(30));
        user.setRevoked(false);
        userRepository.save(user);

        UserModel userModel = userModelRepository.findByUserId(user.getEmail())
                .orElse(null);
        Integer userId = userModel != null ? userModel.getId() : null;

        return new LoginResponse(accessToken, refreshToken, jwtUtil.getAccessTokenValidityInSeconds(), userId);
    }

    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        GlobalUser user = userRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (user.getRevoked() || user.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has been revoked or expired");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String newAccessToken = jwtUtil.generateAccessToken(userDetails, user.getRole().name());

        return new RefreshTokenResponse(newAccessToken, jwtUtil.getAccessTokenValidityInSeconds());
    }

    @Transactional
    public ApiResponse logout(LogoutRequest request) {
        String refreshToken = request.getRefreshToken();

        GlobalUser user = userRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        user.setRevoked(true);
        userRepository.save(user);

        return new ApiResponse(true, "User has been logged out successfully.");
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        Optional<GlobalUser> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            // Per spec: if email already exists, return success with already_existed=true
            // In production, send login page URL to the registered email
            return new RegisterResponse(true, true,
                    "Account already exists. A login page URL has been sent to the registered email address.");
        }

        GlobalUser user = new GlobalUser();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPostalCode(request.getPostalCode());
        user.setPrefecture(request.getPrefecture());
        user.setCity(request.getCity());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            // Per spec: if no password, send login URL for user to set password
            // Set a temporary random password that cannot be used directly
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        }

        try {
            user.setRole(GlobalUser.Role.valueOf(request.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Must be USER, BUYER, or ADMIN");
        }

        user.setRevoked(false);
        userRepository.save(user);

        // In production, send login URL email to user
        return new RegisterResponse(true, false,
                "Account registered. A login URL has been sent to the user's email address.");
    }

    public UserResponse getUserByEmail(String email) {
        GlobalUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse.UserData userData = new UserResponse.UserData(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPostalCode(),
                user.getPrefecture(),
                user.getCity(),
                user.getRole().name(),
                user.getCreatedAt()
        );

        return new UserResponse(true, userData);
    }

    public ApiResponse resetPassword(PasswordResetRequest request) {
        // Per spec: always return 200 OK regardless of whether email exists
        // to prevent account enumeration
        // In production, if user exists, send password reset email
        return new ApiResponse(true, "Password reset email has been sent to the registered email address.");
    }
}
