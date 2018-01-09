package com.eforce21.libraries.security.tokenbased;

import com.eforce21.libraries.security.tokenbased.config.JwtConfig;
import com.eforce21.libraries.security.tokenbased.config.SecurityConfig;
import com.eforce21.libraries.security.tokenbased.exception.InvalidTokenException;
import com.eforce21.libraries.security.tokenbased.impl.TokenProcessor;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Buchner.Benedikt
 * @since 16.08.2017.
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private TokenProcessor tokenService;

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SecurityConfig securityConfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return !pathMatcher.match(securityConfig.getSecuredPath(), request.getServletPath())
               || pathMatcher.match(securityConfig.getLoginUrl(), request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(jwtConfig.getHttpHeaderName());
        try {
            Claims claims = tokenService.verifyToken(token);
            Authentication authentication = tokenService.createAuthenticationFromClaims(claims);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String newToken = tokenService.buildToken(authentication);
            httpServletResponse.setHeader(jwtConfig.getHttpHeaderName(), newToken);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (InvalidTokenException e) {
            LOG.debug("Invalid Token captured", e);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
