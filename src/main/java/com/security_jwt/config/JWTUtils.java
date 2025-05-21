package com.security_jwt.config;

import com.security_jwt.model.Role;
import com.security_jwt.model.User;
import com.security_jwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    private final UserRepository userRepository;
    private final String secret;
    private final Long jwtExpiration;

    public JWTUtils(UserRepository userRepository,
                    @Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration}") Long jwtExpiration) {
        this.userRepository = userRepository;
        this.secret = secret; // Avoid unnecessary Base64 encoding
        this.jwtExpiration = jwtExpiration;
    }

    // Generate JWT token with roles
    public String generateToken(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        Set<Role> roles = user.orElseThrow(() -> new RuntimeException("User not found")).getRoles();

        String rolesString = roles.stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("roles", rolesString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Extract phone number (username) from token
    public String extractUserName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Extract roles from token
    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);

        return Set.of(rolesString.split(","));
    }

    // Validate token and ensure it matches the user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String phoneNumber = extractUserName(token);
            return phoneNumber.equals(userDetails.getUsername());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
