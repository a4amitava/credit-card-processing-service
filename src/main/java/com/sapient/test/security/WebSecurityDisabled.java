package com.sapient.test.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@ConditionalOnProperty(name = "spring.security.enabled", havingValue = "false")
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityDisabled extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.warn("Security is disabled; consider enabling security.");
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("*/*").permitAll();
        http.headers().frameOptions().disable();
    }
}