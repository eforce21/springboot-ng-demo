package com.eforce21.libraries.security.tokenbased.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.eforce21.security.jwt.base", ignoreUnknownFields = false)
public class JwtConfig {

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private String signatureKey;

    private String httpHeaderName = "X-Auth-Token";

    private Long tokenValidity = 1800L;

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    public String getHttpHeaderName() {
        return httpHeaderName;
    }

    public void setHttpHeaderName(String httpHeaderName) {
        this.httpHeaderName = httpHeaderName;
    }

    public Long getTokenValidity() {
        return tokenValidity;
    }

    public void setTokenValidity(Long tokenValidity) {
        this.tokenValidity = tokenValidity;
    }
}
