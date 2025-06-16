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
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // CORS preflight
                        .requestMatchers("/api/auth/**").permitAll() // Login, Register
                        .requestMatchers(HttpMethod.GET, "/barang").permitAll()
                        .requestMatchers(HttpMethod.GET, "/barang/detail").permitAll()
                        .requestMatchers("/barang").permitAll()
                        .requestMatchers("/barang/detail/**").permitAll()

                        // 🛒 Client/cart features
                        .requestMatchers("/api/cart/**").authenticated()
                        .requestMatchers("/api/checkout").authenticated()

                        //User
                        .requestMatchers("/api/user/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/user/delete").authenticated()

                        //Admin
                        .requestMatchers("/products/new").hasRole("Admin")
                        .requestMatchers("/products/delete").hasRole("Admin")
                        .requestMatchers("/products/update").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/product/update/stock").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/barang/update/stock").hasRole("Admin")
                        .requestMatchers("/admin/orders/pending").hasRole("Admin")
                        .requestMatchers(HttpMethod.POST, "/admin/orders/approve/**").hasRole("Admin")

                        // Anything else must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT is stateless
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
