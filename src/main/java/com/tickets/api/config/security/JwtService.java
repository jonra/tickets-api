package com.tickets.api.config.security;

import com.tickets.api.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "2A472D4B6150645367566B59703373367639792F423F4528482B4D6251655468";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public String extractAudience(String token) {
        return extractClaim(token, Claims::getAudience);
    }
    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }
    public List extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }
    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    public String extractTenantId(String token) {
        return extractAllClaims(token).get("tenantId", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity userEntityDetails) {
        HashMap<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("roles", userEntityDetails.getRoles());
        extraClaims.put("email", userEntityDetails.getUsername());
        extraClaims.put("userId", userEntityDetails.getId());
        extraClaims.put("tenantId", userEntityDetails.getTenantId());

        return generateToken(extraClaims, userEntityDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuer("tickets-api")
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
