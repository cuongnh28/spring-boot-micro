package com.demo.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class TaskPoolConfiguration {
    
    @Value("${app.thread-pool.core-size:5}")
    private int corePoolSize;
    
    @Value("${app.thread-pool.max-size:20}")
    private int maxPoolSize;
    
    @Value("${app.thread-pool.queue-capacity:100}")
    private int queueCapacity;
    
    @Value("${app.thread-pool.thread-name-prefix:microservice-async-}")
    private String threadNamePrefix;
    
    @Bean("asyncTaskExecutor")
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setTaskDecorator(new CommonContextTaskDecorator());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        
        executor.initialize();
        
        log.info("Async task executor configured - core: {}, max: {}, queue: {}", 
                corePoolSize, maxPoolSize, queueCapacity);
        
        return executor;
    }
}
