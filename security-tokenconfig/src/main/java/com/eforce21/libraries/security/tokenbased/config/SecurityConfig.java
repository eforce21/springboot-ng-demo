package com.eforce21.libraries.security.tokenbased.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.eforce21.security.base", ignoreUnknownFields = false)
public class SecurityConfig {

    private String securedPath = "/api/**";

    private String loginUrl = "/api/v1/security/login";

    public String getSecuredPath() {
        return securedPath;
    }

    public void setSecuredPath(String securedPath) {
        this.securedPath = securedPath;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
