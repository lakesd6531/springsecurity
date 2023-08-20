package com.example.usermanagement.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.time.OffsetDateTime;
import java.util.Map;

public class JwtUtils {

    // Token 前缀
    public static final String TOKEN_PREFIX = "Bearer ";

    // token 發出 60 min 到期
    private static final Integer TOKEN_EXPIRATION_TIME = 60;

    private static final Key HASH_SALT = MacProvider.generateKey();

    private JwtUtils() {}

    public static String generateToken(Map<String, Object> payload) {

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime accessTokenExpirationDateTime = now.plusMinutes(TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
            .setClaims(payload)
            .setExpiration(TimeUtils.toDate(accessTokenExpirationDateTime))
            .signWith(SignatureAlgorithm.HS256, HASH_SALT).compact();
    }

    public static Jws<Claims> getClaim(String token) {
        Jws<Claims> jwt;
        try {
            jwt = Jwts.parser()
                .setSigningKey(HASH_SALT)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
        } catch (SignatureException | MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "授權憑證錯誤");
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "授權憑證過期，請重新申請");
        }
        return jwt;
    }
}
