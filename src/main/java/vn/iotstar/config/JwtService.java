package vn.iotstar.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.jwt.secret-key}") private String secret;
    @Value("${security.jwt.expiration-time}") private long expirationMs;

    private Key key(){ return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }

    public String generateToken(UserDetails user){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("authorities", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(key())
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().verifyWith((SecretKey)key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }
}