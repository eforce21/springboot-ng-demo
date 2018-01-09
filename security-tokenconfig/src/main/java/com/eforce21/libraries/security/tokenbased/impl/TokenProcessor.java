package com.eforce21.libraries.security.tokenbased.impl;

import com.eforce21.libraries.security.tokenbased.config.JwtConfig;
import com.eforce21.libraries.security.tokenbased.exception.InvalidTokenException;
import com.eforce21.libraries.security.tokenbased.model.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builder and verifier for JSON Web Tokens. This class uses the application
 * properties <code>iarchiv.security.jwt.signature.algorithm</code>
 * <code>iarchiv.security.jwt.signature.keyBase64</code> as the signing
 * algorithm and key for the JWT.
 */
@Component
public class TokenProcessor {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * Build a signed token for the specified subject.
     *
     * @param authentication Base to create authentication token
     * @return The JWT in the standard, URL-safe string encoding.
     */
    public String buildToken(Authentication authentication) {
        String roles = StringUtils.collectionToCommaDelimitedString(authentication.getAuthorities());
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", authentication.getName());
        claims.put("roles", roles);
        return Jwts.builder().setClaims(claims).setExpiration(getExpirationDate())
                .signWith(jwtConfig.getSignatureAlgorithm(), jwtConfig.getSignatureKey())
                .compact();
    }

    /**
     * Extract the {@link Claims} from a signed token, verifying its signature
     * and validity.
     *
     * @param token A JWT in the standard, URL-safe string encoding.
     * @return The {@link Claims} of the JWT. Never <code>null</code>.
     * @throws InvalidTokenException Decoding failed, signature verification failed, or token
     *                               expired.
     */
    public Claims verifyToken(String token) throws InvalidTokenException {
        try {
            return Jwts.parser().setSigningKey(jwtConfig.getSignatureKey()).parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Could not validate token", e);
        }
    }

    private Date getExpirationDate() {
        return new Date(Instant.now().plusSeconds(jwtConfig.getTokenValidity()).toEpochMilli());
    }

    public Authentication createAuthenticationFromClaims(Claims claims) {
        String username = claims.getSubject();
        String roles = (String) claims.get("roles");
        Collection<String> authorities = StringUtils.commaDelimitedListToSet(roles);

        SecurityUser user = new SecurityUser();
        user.setName(username);
        user.setAuthorities(authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        user.setAuthenticated(true);

        return user;
    }
}
