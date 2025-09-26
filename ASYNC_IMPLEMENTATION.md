# Async Implementation with Context Preservation

This document explains the complete async implementation in the Spring Boot microservices project, including context preservation across thread boundaries.

## 🎯 Overview

The async implementation provides:
- **Context Preservation**: Correlation IDs and user context are maintained across async operations
- **Configurable Thread Pools**: Customizable thread pool settings via application properties
- **Multiple Usage Patterns**: Support for `@Async` annotations and manual async operations
- **Error Handling**: Proper error handling and graceful shutdown
- **Performance Monitoring**: Logging and monitoring of async operations

## 🏗️ Architecture

### Core Components

1. **AsyncConfig** - Main configuration class
2. **CommonContextTaskDecorator** - Context preservation decorator
3. **AsyncUtils** - Utility class for async operations
4. **AsyncExampleService** - Example service demonstrating usage

### Thread Pool Configuration

```yaml
app:
  async:
    core-pool-size: 5          # Minimum number of threads
    max-pool-size: 20          # Maximum number of threads
    queue-capacity: 100        # Queue size before creating new threads
    thread-name-prefix: microservice-async-  # Thread naming
    keep-alive-seconds: 60     # Thread idle timeout
    await-termination-seconds: 30  # Shutdown timeout
```

## 🚀 Usage Patterns

### 1. Using @Async Annotation

```java
@Service
public class ProductService {
    
    @Async
    public CompletableFuture<String> processProductAsync(String productId) {
        // Context is automatically preserved
        String correlationId = CommonContextHolder.getCorrelationId();
        String userId = CommonContextHolder.getUserId();
        
        log.info("Processing product {} with correlationId: {}", productId, correlationId);
        
        // Simulate processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return CompletableFuture.completedFuture("Product processed: " + productId);
    }
}
```

### 2. Using AsyncUtils

```java
@Service
public class UserService {
    
    public CompletableFuture<User> createUserAsync(UserRequest request) {
        return AsyncUtils.supplyAsync(() -> {
            // Context is automatically preserved
            String correlationId = CommonContextHolder.getCorrelationId();
            String currentUserId = CommonContextHolder.getUserId();
            
            log.info("Creating user with correlationId: {}, by user: {}", 
                    correlationId, currentUserId);
            
            // Create user logic
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            
            return userRepository.save(user);
        });
    }
}
```

### 3. Chaining Async Operations

```java
@Service
public class OrderService {
    
    public CompletableFuture<Order> processOrderAsync(OrderRequest request) {
        return AsyncUtils.supplyAsync(() -> {
            // Step 1: Validate order
            log.info("Validating order - correlationId: {}", 
                    CommonContextHolder.getCorrelationId());
            return validateOrder(request);
        })
        .thenCompose(validatedOrder -> AsyncUtils.supplyAsync(() -> {
            // Step 2: Calculate pricing
            log.info("Calculating pricing - correlationId: {}", 
                    CommonContextHolder.getCorrelationId());
            return calculatePricing(validatedOrder);
        }))
        .thenCompose(pricedOrder -> AsyncUtils.supplyAsync(() -> {
            // Step 3: Save order
            log.info("Saving order - correlationId: {}", 
                    CommonContextHolder.getCorrelationId());
            return orderRepository.save(pricedOrder);
        }));
    }
}
```

### 4. Error Handling

```java
@Service
public class PaymentService {
    
    public CompletableFuture<PaymentResult> processPaymentAsync(PaymentRequest request) {
        return AsyncUtils.supplyAsync(() -> {
            String correlationId = CommonContextHolder.getCorrelationId();
            log.info("Processing payment - correlationId: {}", correlationId);
            
            if (request.getAmount() <= 0) {
                throw new IllegalArgumentException("Invalid payment amount");
            }
            
            // Process payment logic
            return processPayment(request);
        })
        .handle((result, throwable) -> {
            if (throwable != null) {
                log.error("Payment processing failed - correlationId: {}, error: {}", 
                         CommonContextHolder.getCorrelationId(), throwable.getMessage());
                return PaymentResult.failed(throwable.getMessage());
            }
            return result;
        });
    }
}
```

## 🔧 Configuration Details

### AsyncConfig Class

```java
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    
    @Bean("asyncTaskExecutor")
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Basic configuration
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        
        // Context preservation
        executor.setTaskDecorator(new CommonContextTaskDecorator());
        
        // Rejection policy
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        // Graceful shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        
        return executor;
    }
    
    @Override
    public Executor getAsyncExecutor() {
        return asyncTaskExecutor();
    }
}
```

### Context Preservation

The `CommonContextTaskDecorator` automatically:
1. **Captures** current context (correlation ID, user info, security context)
2. **Restores** context in the new thread
3. **Cleans up** context after execution

## 📊 Monitoring and Logging

### Thread Pool Metrics

The implementation provides logging for:
- Thread pool configuration on startup
- Async operation execution
- Context preservation
- Error handling

### Example Logs

```
2024-01-15 10:30:00 - Async TaskExecutor configured - core: 5, max: 20, queue: 100, prefix: product-async-
2024-01-15 10:30:01 - Executing async task with context preservation - correlationId: abc-123
2024-01-15 10:30:01 - Processing in async thread - correlationId: abc-123, userId: 456, username: john.doe
2024-01-15 10:30:02 - Async processing completed - correlationId: abc-123
```

## 🎯 Best Practices

### 1. Use Appropriate Async Pattern

- **@Async**: For simple fire-and-forget operations
- **AsyncUtils**: For complex operations requiring chaining or error handling

### 2. Always Handle Errors

```java
CompletableFuture<String> future = AsyncUtils.supplyAsync(() -> {
    // Your async logic
    return "result";
})
.handle((result, throwable) -> {
    if (throwable != null) {
        log.error("Async operation failed", throwable);
        return "error";
    }
    return result;
});
```

### 3. Configure Thread Pools Appropriately

- **Core Pool Size**: Start with 5-10 threads
- **Max Pool Size**: Set based on expected load
- **Queue Capacity**: Balance between memory usage and responsiveness

### 4. Monitor Performance

- Use logging to track async operation performance
- Monitor thread pool utilization
- Set up alerts for queue capacity and rejection rates

## 🚨 Common Pitfalls

### 1. Missing @EnableAsync

Without `@EnableAsync`, `@Async` annotations are ignored.

### 2. Self-Invocation

`@Async` doesn't work when calling methods on the same class:

```java
// ❌ This won't work
public void process() {
    this.processAsync(); // Self-invocation
}

@Async
public void processAsync() {
    // This won't be async
}
```

### 3. Context Loss

Always use the configured `TaskExecutor` or `AsyncUtils` to preserve context.

### 4. Blocking Operations

Avoid blocking operations in async methods:

```java
// ❌ Don't do this
@Async
public CompletableFuture<String> badAsync() {
    return CompletableFuture.completedFuture(blockingOperation());
}
```

## 🔍 Testing Async Operations

### Unit Testing

```java
@Test
public void testAsyncOperation() {
    // Test the async operation
    CompletableFuture<String> future = service.processAsync("test");
    
    // Wait for completion
    String result = future.get(5, TimeUnit.SECONDS);
    
    // Assert result
    assertThat(result).isEqualTo("Processed: test");
}
```

### Integration Testing

```java
@Test
public void testAsyncWithContext() {
    // Set up context
    CommonContextHolder.setContext(createTestContext(), true);
    
    // Execute async operation
    CompletableFuture<String> future = service.processAsync("test");
    
    // Verify context is preserved
    String result = future.get(5, TimeUnit.SECONDS);
    assertThat(result).contains("correlationId: test-123");
}
```

## 📈 Performance Considerations

### Thread Pool Sizing

- **CPU-bound tasks**: Thread count ≈ CPU cores
- **I/O-bound tasks**: Thread count > CPU cores (typically 2-4x)
- **Mixed workloads**: Start with 2x CPU cores and adjust based on monitoring

### Memory Usage

- Each thread consumes ~1MB of memory
- Monitor total memory usage with thread pools
- Use appropriate queue sizes to prevent memory issues

### Monitoring

- Monitor thread pool metrics via Actuator endpoints
- Set up alerts for queue capacity and rejection rates
- Track async operation performance and error rates

## 🎉 Conclusion

The async implementation provides a robust foundation for asynchronous operations in microservices while maintaining context preservation. It supports multiple usage patterns and provides comprehensive configuration options for different use cases.

Key benefits:
- ✅ Context preservation across threads
- ✅ Configurable thread pools
- ✅ Multiple usage patterns
- ✅ Error handling and monitoring
- ✅ Graceful shutdown
- ✅ Performance optimization












