package com.example.usermanagement.config;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String jwtToken;
    private String userId;
    private String name;

    public JwtAuthenticationToken(
        Object principal,
        Object credentials,
        Collection<? extends GrantedAuthority> authorities,
        String jwtToken,
        String userId,
        String name) {

        super(principal, credentials, authorities);
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.name = name;
    }
}
