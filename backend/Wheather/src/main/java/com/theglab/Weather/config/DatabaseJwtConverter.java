package com.theglab.Weather.config;

import com.theglab.Weather.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DatabaseJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserService userService;

    @Autowired
    public DatabaseJwtConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        if (email == null) {
            throw new IllegalArgumentException("JWT does not contain 'email' claim");
        }

        List<String> roles = userService.findRolesByEmail(email); // ["ROLE_USER", "ROLE_ADMIN"]
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(jwt, null, authorities);
    }
}
