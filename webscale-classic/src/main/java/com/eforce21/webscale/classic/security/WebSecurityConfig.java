package com.eforce21.webscale.classic.security;

import com.eforce21.libraries.security.tokenbased.DefaultTokenBasedConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Order(1)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends DefaultTokenBasedConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
    }
}
