package com.eforce21.libraries.security.tokenbased;

import com.eforce21.libraries.security.tokenbased.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.eforce21.libraries.security")
public abstract class DefaultTokenBasedConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private TokenBasedAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, securityConfig.getSecuredPath()).permitAll()
                .antMatchers(securityConfig.getSecuredPath()).authenticated()
                .antMatchers(securityConfig.getLoginUrl()).permitAll()
                .anyRequest().permitAll()
            .and().formLogin()
                .loginProcessingUrl(securityConfig.getLoginUrl())
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler);
    }
}
