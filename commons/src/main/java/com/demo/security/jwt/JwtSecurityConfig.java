package com.demo.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtSecurityConfig {

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }
}

