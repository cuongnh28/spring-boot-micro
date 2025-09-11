package com.demo.feign;

import com.demo.dto.User;
import com.demo.config.feign.CommonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "http://account-service-app:8088", configuration = {CommonFeignConfig.class})
public interface UserFeignClient {

    @GetMapping("/api/user/{id}")
    User getAccountInfo(@PathVariable Long id);

}
