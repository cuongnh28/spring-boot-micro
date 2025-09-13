package com.demo.config.thread;

import com.demo.context.CommonContextHolder;
import com.demo.util.CorrelationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ThreadContextExampleService {
    
    public CompletableFuture<String> processAsync(String data) {
        log.info("Starting async processing with correlationId: {}", 
                CommonContextHolder.getCorrelationId());
        
        return ThreadContextAsyncExecutor.supplyAsync(() -> {
            String correlationId = CommonContextHolder.getCorrelationId();
            String userId = CommonContextHolder.getUserId();
            String username = CommonContextHolder.getUsername();
            
            log.info("Processing in async thread - correlationId: {}, userId: {}, username: {}", 
                    correlationId, userId, username);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            return "Processed: " + data + " by user: " + username;
        });
    }
    
    public void setContextExample() {
        CommonContext context = CommonContextHolder.createFromSecurityContext();
        CommonContextHolder.setContext(context, true);
        
        log.info("Context set - correlationId: {}, userId: {}", 
                context.getCorrelationId(), context.getUserId());
    }
    
    public String getContextInfo() {
        String correlationId = CommonContextHolder.getCorrelationId();
        String userId = CommonContextHolder.getUserId();
        String username = CommonContextHolder.getUsername();
        
        return String.format("Context - correlationId: %s, userId: %s, username: %s", 
                correlationId, userId, username);
    }
}
