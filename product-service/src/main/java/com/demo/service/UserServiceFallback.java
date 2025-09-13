package com.demo.service;

import com.demo.dto.User;
import com.demo.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserServiceFallback implements UserFeignClient {

    @Override
    public User getAccountInfo(Long id) {
        log.warn("Circuit breaker opened - using fallback for user ID: {}", id);
        
        // Return a default user or cached user
        User fallbackUser = new User();
        fallbackUser.setId(id);
        fallbackUser.setUsername("fallback-user");
        fallbackUser.setEmail("fallback@example.com");
        fallbackUser.setEnabled(true);
        
        return fallbackUser;
    }
}
