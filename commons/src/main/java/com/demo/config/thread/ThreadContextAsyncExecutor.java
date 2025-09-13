package com.demo.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Slf4j
@Component
public class ThreadContextAsyncExecutor {
    
    private static TaskExecutor taskExecutor;
    
    @Autowired
    public void setTaskExecutor(@Qualifier("asyncTaskExecutor") TaskExecutor taskExecutor) {
        ThreadContextAsyncExecutor.taskExecutor = taskExecutor;
    }
    
    public static CompletableFuture<Void> runAsync(Runnable task) {
        if (taskExecutor == null) {
            log.warn("TaskExecutor not initialized, falling back to default executor");
            return CompletableFuture.runAsync(task);
        }
        return CompletableFuture.runAsync(task, taskExecutor);
    }
    
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> task) {
        if (taskExecutor == null) {
            log.warn("TaskExecutor not initialized, falling back to default executor");
            return CompletableFuture.supplyAsync(task);
        }
        return CompletableFuture.supplyAsync(task, taskExecutor);
    }
    
    public static CompletableFuture<Void> runAsync(Runnable task, TaskExecutor executor) {
        return CompletableFuture.runAsync(task, executor);
    }
    
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> task, TaskExecutor executor) {
        return CompletableFuture.supplyAsync(task, executor);
    }
}
