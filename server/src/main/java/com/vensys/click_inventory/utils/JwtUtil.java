package com.vensys.click_inventory.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
  private final String SECRET_KEY = "m0sQmXkzjkLcx8BX9T2uwryX2G368AuSMrG5xVGxrAZ";

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username) {
    return Jwts.builder()
            .subject(username) // Pengganti setSubject
            .issuedAt(new Date()) // Pengganti setIssuedAt
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 jam
            .signWith(getSigningKey()) // Di versi 0.13.0, algoritma dideteksi otomatis dari key
            .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey()) // Pengganti setSigningKey
            .build()
            .parseSignedClaims(token) // Pengganti parseClaimsJws
            .getPayload() // Pengganti getBody
            .getSubject();
  }
}
