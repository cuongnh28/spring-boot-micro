package com.demo.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AuthorizationFeignConfig implements RequestInterceptor {
    @Value("${security.authorization.client-id}")
    private String clientId;

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        Long currentUserId = SecurityUtils.getCurrentUserId();
//        String originalClientId = SecurityUtils.getOriginalClientId();
        if (this.clientId != null) {
            requestTemplate.header("X-Client-Id", clientId);
        }

//        if (originalClientId != null) {
//            requestTemplate.header("X-Original-Client-Id", originalClientId);
//        }
//
//        if (currentUserId != null) {
//            requestTemplate.header("X-User-Id", String.format("%s", currentUserId));
//        }
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AuthorizationErrorDecoder();
    }
}