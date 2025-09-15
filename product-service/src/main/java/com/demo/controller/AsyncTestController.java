package com.demo.controller;

import com.demo.service.AsyncExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * Test controller to demonstrate async functionality with context preservation.
 */
@Slf4j
@RestController
@RequestMapping("/api/async-test")
@RequiredArgsConstructor
public class AsyncTestController {

    private final AsyncExampleService asyncExampleService;

    /**
     * Test endpoint for @Async annotation with context preservation.
     */
    @GetMapping("/annotation/{data}")
    public CompletableFuture<ResponseEntity<String>> testAsyncAnnotation(@PathVariable String data) {
        log.info("Testing @Async annotation with data: {}", data);
        
        return asyncExampleService.processWithAsyncAnnotation(data)
                .thenApply(result -> {
                    log.info("Async annotation test completed: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Test endpoint for AsyncUtils with context preservation.
     */
    @GetMapping("/utils/{data}")
    public CompletableFuture<ResponseEntity<String>> testAsyncUtils(@PathVariable String data) {
        log.info("Testing AsyncUtils with data: {}", data);
        
        return asyncExampleService.processWithAsyncUtils(data)
                .thenApply(result -> {
                    log.info("AsyncUtils test completed: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Test endpoint for chained async operations.
     */
    @GetMapping("/chain/{data}")
    public CompletableFuture<ResponseEntity<String>> testAsyncChaining(@PathVariable String data) {
        log.info("Testing async chaining with data: {}", data);
        
        return asyncExampleService.processWithChaining(data)
                .thenApply(result -> {
                    log.info("Async chaining test completed: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Test endpoint for error handling in async operations.
     */
    @GetMapping("/error/{data}")
    public CompletableFuture<ResponseEntity<String>> testAsyncErrorHandling(@PathVariable String data) {
        log.info("Testing async error handling with data: {}", data);
        
        return asyncExampleService.processWithErrorHandling(data)
                .thenApply(result -> {
                    log.info("Async error handling test completed: {}", result);
                    return ResponseEntity.ok(result);
                });
    }

    /**
     * Test endpoint to verify async configuration is loaded.
     */
    @GetMapping("/config-status")
    public ResponseEntity<String> getConfigStatus() {
        log.info("Checking async configuration status");
        
        // This will only work if AsyncConfig is properly loaded
        return ResponseEntity.ok("Async configuration is loaded and working! " +
                "Try the other endpoints to test async operations with context preservation.");
    }
}






