package com.restaurant.Security;

import com.restaurant.Dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtAuthenticationProvider {

    private static final String SECRET_KEY = "BWgwvfUQbvfyhCHdIlcrFDn8/dAlonrY+Kj+DJ8mhSkkFl2v+XDv/M1KK9jWO+Bi0PF88wwTmkciUO3x7/d9Mqo/lv+5qk/Fz/YM97ylIEXhAz/e/1iPC95+f3UoyiIxTYf5z2vRfKu6twhjUwMZ+9eKiLfxqkHpOqAmWZhPSr2TRL+V91I303uyoYy7cU8LiX0LVkvXvTDX4vS7wdvGPvLIUxBzC1I/Ko6n+5oHNZm+bQhoph0SEHP8ne9n6EZQ8/sMJNs8wLX7qyuanD8KhkAnn+vFTu4PTzNnV+JwUGN1L87qRiZ7CIoNwasazvgu0rvFZH5g3Hx2kVRM82zznCmbWgYdCCR0vpR1Ourj23M=";

    // Generate token using UserDto
    public String generateToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDto.getId()); // ✅ Must be present
        claims.put("username", userDto.getUsername());
        claims.put("email", userDto.getEmail());
        claims.put("role", userDto.getRole());

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDto.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(getSignInKey())
                .compact();
    }


    // Get username (email) from token
    public String getUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // You decide how to validate it (compare subject with expected email, etc.)
    public boolean isTokenValid(String token, String expectedEmail){
        final String email = getUsername(token);
        return (email.equals(expectedEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return getExpirationDate(token).before(new Date());
    }

    private Date getExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}