package com.security_jwt.config;

import com.security_jwt.model.Role;
import com.security_jwt.model.User;
import com.security_jwt.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class JWTUtils {

    private final UserRepository userRepository;

    private String secret;

    private Long jwtExpiration;

    //@Value should not come from lombok


    @Autowired
    public JWTUtils(UserRepository userRepository, @Value("${auth.jwt.secret}") String secret,
                    @Value("${auth.jwt.expiration}") Long jwtExpiration) {
        super();
        this.userRepository = userRepository;
        this.secret = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
    }

    //token generation
    public String generateToken(String phoneNumber) {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        Set<Role> roles = user.get().getRoles();

        //add roles to the token

        return Jwts.builder().setSubject(phoneNumber).claim("roles", roles.stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    //extract user name
    public String extractToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJwt(token).getBody().getSubject();
    }

    //extract roles
    public Set<String> extractRoles(String token) {
        String rolesString = Jwts.parserBuilder().setSigningKey(secret)
                .build().parseClaimsJwt(token).getBody().get("roles", String.class);
        return Set.of(rolesString);
    }

    //token validation
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJwt(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }


}
