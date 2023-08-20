package com.example.usermanagement.config;

import com.example.usermanagement.dto.JwtAuthenticationTokenDTO;
import com.example.usermanagement.service.JwtAuthenticationService;
import com.example.usermanagement.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationService jwtAuthenticationService;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain)
        throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || header.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // token 類別錯誤
        if (!header.startsWith(JwtUtils.TOKEN_PREFIX)) {
            throw new BadCredentialsException("token 類別錯誤");
        }

        String token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
            .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("請登入後獲得授權"));

        JwtAuthenticationTokenDTO jwtAuthenticationTokenDTO = jwtAuthenticationService.authenticateJwt(token);

        JwtAuthenticationToken authenticationToken = toJwtAuthenticationToken(jwtAuthenticationTokenDTO);

        // 提供給後續 API 可能會操作到權限相關的人使用 authenticationToken.
        SecurityContextHolder.getContext()
            .setAuthentication(
                authenticationToken
            );

        doFilter(request, response, filterChain);
    }

    private JwtAuthenticationToken toJwtAuthenticationToken(JwtAuthenticationTokenDTO jwtDTO) {
        return new
            JwtAuthenticationToken(
            jwtDTO.getUserId(),
            null,
            getAuthorities(jwtDTO.getRoleNameList()),
            jwtDTO.getJwtToken(),
            jwtDTO.getUserId(),
            jwtDTO.getName()
        );
    }

    private Set<GrantedAuthority> getAuthorities(List<String> roleNameList) {
        return roleNameList.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    }
}
