package com.example.study.utils;

import com.example.study.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    private final SecretKey secretKey;

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        this.secretKey = jwtConfig.generateKey();
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userId, String userType) {
        return Jwts.builder()
                .subject(userId).issuedAt(new Date())
                .claim("userType", userType)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey).compact();
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .issuedAt(new Date())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey).compact();
    }

    public Boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        log.info("validateToken : {}, extractedUserId : {}", token, extractedUserId);
        return (userId.equals(extractedUserId) && !isTokenExpired(token));
    }

    public String extractUserType(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("userType", String.class);
    }
}
