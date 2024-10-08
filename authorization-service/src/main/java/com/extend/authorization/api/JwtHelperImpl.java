package com.extend.authorization.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHelperImpl implements JwtHelper {
    private final String secretKey = "myReallyReallyReallyLongJwtSecretKey";

    public boolean isTokenValid(String token) {

        Date expiration;
        try {
            expiration = extractExpiration(token);
            if (expiration == null) {
                return false;
            }
        } catch (SignatureException e) {
            return false;
        }
        return expiration.after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Claims extractAllClaims(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            return null;
        }
    }
}
