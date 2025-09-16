package com.demo.controller;

import com.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-events")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class ProductEventController {

    @Autowired
    private UserService userService;

    /**
     * Test endpoint to simulate product event processing
     * This demonstrates how the account service would handle product events
     */
    @PostMapping("/test/{userId}")
    public String testProductEvent(@PathVariable Long userId, @RequestParam String action) {
        log.info("Testing product event processing for user {} with action {}", userId, action);
        
        userService.updateUserProductStats(userId, action);
        
        return String.format("Product event processed for user %d with action: %s", userId, action);
    }
}






