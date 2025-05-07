package com.theglab.Weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpointy publiczne – dostępne bez logowania
                        .requestMatchers("/api/public/**").permitAll()

                        // 🔐 Endpointy dostępne po zalogowaniu – zwykli użytkownicy
                        .requestMatchers("/api/simple/**").hasAnyRole("USER", "ADMIN")

                        // 🔐 Endpointy tylko dla administratorów
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ❗ Wszystko inne – wymaga uwierzytelnienia
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new CustomJwtConverter()))
                );

        return http.build();
    }
}
