package com.example.rest_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow CORS preflight requests
                        .requestMatchers("/api/auth/**").permitAll() // Authentication-related endpoints
                        .requestMatchers(HttpMethod.GET, "/barang").permitAll() // Public product listing
                        .requestMatchers(HttpMethod.GET, "/barang/detail").permitAll() // Public product details
                        .requestMatchers("/barang").permitAll()
                        .requestMatchers("/barang/detail").permitAll()
                        .requestMatchers("/barang/detail/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/user/delete").authenticated()// Changed to cover all user endpoints
                        .requestMatchers("/products/new").hasRole("Admin")// Changed to cover all user endpoints
                        .requestMatchers("/products/delete").hasRole("Admin")// Changed to cover all user endpoints
                        .requestMatchers("/products/update").hasRole("Admin")
                        .requestMatchers("/products/update/stock").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/product/update/stock").hasRole("Admin") // Restricting this endpoint to ADMIN role
                        .requestMatchers(HttpMethod.PUT, "/barang/update/stock").hasRole("Admin")
                        .requestMatchers("/api/user/changerole").hasRole("Admin")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session for JWT
                );

        // Add JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allow frontend origin
        config.setAllowedMethods(Arrays.asList("*")); // Allow all HTTP methods
        config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        config.setExposedHeaders(Arrays.asList("Authorization")); // Expose Authorization header for JWT
        config.setAllowCredentials(true); // Allow cookies (if needed)
        config.setMaxAge(3600L); // Cache CORS configuration for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to all endpoints
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}