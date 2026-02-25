package com.ctn.commonauthentication.authService;

import com.ctn.commonauthentication.dto.LoginResponse;
import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.entity.GlobalUser;
import com.ctn.commonauthentication.entity.UserProfile;
import com.ctn.commonauthentication.repository.GlobalUserRepository;
import com.ctn.commonauthentication.repository.UserModelRepository;
import com.ctn.commonauthentication.repository.UserProfileRepository;
import com.ctn.commonauthentication.security.JwtUtil;
import com.ctn.commonauthentication.service.IInternalEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final GlobalUserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserModelRepository userModelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final IInternalEmailService emailService;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        GlobalUser user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userId = userModelRepository.findByUserId(user.getEmail())
                .map(u -> String.valueOf(u.getId()))
                .orElse(String.valueOf(user.getId()));

        String accessToken = jwtUtil.generateAccessToken(userDetails, user.getRole().name(), userId);
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setToken(refreshToken);
        user.setExpiresAt(LocalDateTime.now().plusDays(30));
        user.setRevoked(false);
        userRepository.save(user);

        return new LoginResponse(accessToken, refreshToken, jwtUtil.getAccessTokenValidityInSeconds());
    }

    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String incomingRefreshToken = request.getRefreshToken();

        if (!jwtUtil.validateRefreshToken(incomingRefreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        GlobalUser user = userRepository.findByToken(incomingRefreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (user.getRevoked() || user.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has been revoked or expired");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String userId = userModelRepository.findByUserId(user.getEmail())
                .map(u -> String.valueOf(u.getId()))
                .orElse(String.valueOf(user.getId()));
        String newAccessToken = jwtUtil.generateAccessToken(userDetails, user.getRole().name(), userId);
        // Rotate the refresh token for improved security
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setToken(newRefreshToken);
        user.setExpiresAt(LocalDateTime.now().plusDays(30));
        user.setRevoked(false);
        userRepository.save(user);

        return new LoginResponse(newAccessToken, newRefreshToken, jwtUtil.getAccessTokenValidityInSeconds());
    }

    @Transactional
    public void logout(String email) {
        GlobalUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRevoked(true);
        user.setToken(null);
        userRepository.save(user);
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        Optional<GlobalUser> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            GlobalUser existing = existingUser.get();
            String existingUserId = "usr_" + existing.getId();
            return new RegisterResponse(existingUserId, existing.getEmail(), "ALREADY_EXISTS");
        }

        GlobalUser user = new GlobalUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(GlobalUser.Status.ACTIVE);

        try {
            user.setSourceSystem(GlobalUser.SourceSystem.valueOf(request.getSourceSystem().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid source_system. Must be SALES or PURCHASE");
        }

        String role = (request.getRole() != null && !request.getRole().isBlank()) ? request.getRole() : "USER";
        try {
            user.setRole(GlobalUser.Role.valueOf(role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role. Must be USER");
        }

        user.setRevoked(false);
        GlobalUser savedUser = userRepository.save(user);

        UserProfile profile = new UserProfile();
        profile.setGlobalUser(savedUser);
        profile.setName(request.getName());

        // Strip non-numeric characters from the phone number before saving
        String phone = request.getPhone();
        if (phone != null) {
            phone = phone.replaceAll("[^0-9]", "");
        }
        profile.setPhoneNumber(phone);

        // Strip non-numeric characters from the postal code before saving
        String postalCode = request.getPostalCode();
        if (postalCode != null) {
            postalCode = postalCode.replaceAll("[^0-9]", "");
        }
        profile.setPostalCode(postalCode);
        profile.setPrefecture(request.getPrefecture());
        profile.setCity(request.getCity());
        userProfileRepository.save(profile);

        String userId = "usr_" + savedUser.getId();
        return new RegisterResponse(userId, savedUser.getEmail(), "REGISTERED");
    }

    public UserProfileResponse getUserProfile(String email) {
        GlobalUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository.findByGlobalUser(user).orElse(null);

        return new UserProfileResponse(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus() != null ? user.getStatus().name() : GlobalUser.Status.ACTIVE.name(),
                profile != null ? profile.getName() : null,
                profile != null ? profile.getPhoneNumber() : null,
                profile != null ? profile.getPostalCode() : null,
                profile != null ? profile.getPrefecture() : null,
                profile != null ? profile.getCity() : null);
    }

    @Transactional
    public void forgotPassword(PasswordForgotRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        Optional<GlobalUser> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("USER_NOT_FOUND");
        }

        GlobalUser user = userOpt.get();
        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetExpiresAt(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), resetToken, user.getId().intValue());
        log.info("[PASSWORD RESET] Reset email sent to {}", user.getEmail());
    }

    @Transactional
    public void resetPassword(PasswordResetExecuteRequest request) {
        GlobalUser user = userRepository.findByPasswordResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("INVALID_TOKEN"));

        if (!user.getEmail().equals(request.getEmail())) {
            throw new RuntimeException("INVALID_TOKEN");
        }

        if (user.getPasswordResetExpiresAt() == null
                || user.getPasswordResetExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("EXPIRED_TOKEN");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiresAt(null);
        user.setRevoked(true);
        user.setToken(null);
        userRepository.save(user);
    }
}
