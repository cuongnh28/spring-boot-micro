package com.demo.config.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public final class ThreadContextAsyncExecutor {
    private static TaskExecutor taskExecutor;

    @Autowired
    public void setTaskExecutor(@Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        ThreadContextAsyncExecutor.taskExecutor = taskExecutor;
    }

    public static CompletableFuture<Void> runAsyncWithContext(Runnable task) {
        return CompletableFuture.runAsync(task::run, taskExecutor);
    }

    public static <T> CompletableFuture<T> supplyAsyncWithContext(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, taskExecutor);
    }
}
