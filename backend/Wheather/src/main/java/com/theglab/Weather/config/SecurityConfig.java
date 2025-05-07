package com.theglab.Weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final DatabaseJwtConverter databaseJwtConverter;

    public SecurityConfig(DatabaseJwtConverter databaseJwtConverter) {
        this.databaseJwtConverter = databaseJwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/public/createNewUser")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/public/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/simple/**")).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // <-- ZMIANA
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasAuthority("ROLE_ADMIN") // <-- ZMIANA
                        .anyRequest().authenticated()

                )
                .cors(cors -> {})
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(databaseJwtConverter))
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

