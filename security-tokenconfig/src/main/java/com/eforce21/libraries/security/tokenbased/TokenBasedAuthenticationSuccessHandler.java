package com.eforce21.libraries.security.tokenbased;

import com.eforce21.libraries.security.tokenbased.config.JwtConfig;
import com.eforce21.libraries.security.tokenbased.impl.TokenProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Buchner.Benedikt
 * @since 15.08.2017.
 */
@Component
public class TokenBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenProcessor tokenService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String authenticationToken = tokenService.buildToken(authentication);
        httpServletResponse.setHeader(jwtConfig.getHttpHeaderName(), authenticationToken);
    }
}
