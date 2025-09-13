package com.demo.feign;

import com.demo.dto.User;
import com.demo.config.feign.CommonFeignConfig;
import com.demo.service.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "account-service", 
    url = "http://account-service-app:8088", 
    configuration = {CommonFeignConfig.class},
    fallback = UserServiceFallback.class
)
public interface UserFeignClient {

    @GetMapping("/api/user/{id}")
    User getAccountInfo(@PathVariable Long id);

}
