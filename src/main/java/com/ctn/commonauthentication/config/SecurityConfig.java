package com.ctn.commonauthentication.config;

import com.ctn.commonauthentication.security.CustomUserDetailsService;
import com.ctn.commonauthentication.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "https://d3emmx88z2xdno.cloudfront.net",
                "https://d3hr6jxhk0c2mf.cloudfront.net",
                "https://master.d1zcih8gqql0zz.amplifyapp.com",
                "https://ctn-net.jp",
                "https://d3ul0twcmnvo72.cloudfront.net",
                "https://check-demo.net",
                "https://61.213.68.163",
                "http://61.213.68.163",
                "https://development.da9f2us1k1n15.amplifyapp.com",
                "https://kaitori-sakurai.com",
                "http://carhoo.co.jp",
                "https://carhoo.co.jp",
                "https://www.d3emmx88z2xdno.cloudfront.net",
                "https://www.d3hr6jxhk0c2mf.cloudfront.net",
                "https://www.master.d1zcih8gqql0zz.amplifyapp.com",
                "https://www.ctn-net.jp",
                "https://www.d3ul0twcmnvo72.cloudfront.net",
                "https://www.check-demo.net",
                "https://www.development.da9f2us1k1n15.amplifyapp.com",
                "https://www.kaitori-sakurai.com",
                "https://www.carhoo.co.jp",
                "http://check-demo.net",
                "https://carhoo.check-demo.net",
                "https://user-mypage.ctn-net.jp",
                "https://buyer-mypage.ctn-net.jp",
                "https://admin.ctn-net.jp",
                "https://ad5-test.ctn-net.jp",
                "https://inse-test.ctn-net.jp",
                "https://ctn-demo.com",
                "http://localhost:5173/",
                "http://localhost:3000",
                "http://localhost:8080"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication required)
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/register",
                                "/api/v1/auth/refresh",
                                "/api/v1/auth/password/forgot",
                                "/api/v1/auth/password/reset")
                        .permitAll()
                        // Swagger / OpenAPI docs
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Protected endpoints (BearerAuth required)
                        // /api/v1/auth/logout and /api/v1/auth/me go through JWT filter
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}