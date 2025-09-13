# Thread Context Configuration

This package provides thread context management for microservices, ensuring that correlation IDs and user context are preserved across async operations.

## Components

### 1. CommonContext Interface
Defines the contract for thread-local context information:
- `correlationId`: Request tracing identifier
- `userId`: Current user ID
- `username`: Current username

### 2. DefaultCommonContext
Simple implementation of CommonContext interface.

### 3. CommonContextHolder
Thread-local storage for context information with support for:
- Regular thread-local storage
- Inheritable thread-local storage (for child threads)
- Integration with Spring Security context

### 4. CommonContextTaskDecorator
Task decorator that preserves context across async operations:
- Captures current context before async execution
- Restores context in the new thread
- Cleans up context after execution

### 5. TaskPoolConfiguration
Configures thread pool with context preservation:
- Configurable pool sizes via application properties
- Uses CommonContextTaskDecorator for context preservation
- Proper shutdown handling

### 6. ThreadContextAsyncExecutor
Utility class for executing async operations with context preservation.

## Usage

### Basic Async Operations
```java
@Service
public class MyService {
    
    public CompletableFuture<String> processAsync(String data) {
        return ThreadContextAsyncExecutor.supplyAsync(() -> {
            // Context is automatically preserved here
            String correlationId = CommonContextHolder.getCorrelationId();
            String userId = CommonContextHolder.getUserId();
            
            // Your async logic here
            return "Processed: " + data;
        });
    }
}
```

### Manual Context Management
```java
// Set context manually
CommonContext context = CommonContextHolder.createFromSecurityContext();
CommonContextHolder.setContext(context, true); // true = inheritable

// Get context information
String correlationId = CommonContextHolder.getCorrelationId();
String userId = CommonContextHolder.getUserId();
String username = CommonContextHolder.getUsername();

// Clear context
CommonContextHolder.resetContext();
```

### Configuration Properties
Add these to your `application.properties`:
```properties
# Thread pool configuration
app.thread-pool.core-size=5
app.thread-pool.max-size=20
app.thread-pool.queue-capacity=100
app.thread-pool.thread-name-prefix=microservice-async-
```

## Benefits

1. **Request Tracing**: Correlation IDs are preserved across all async operations
2. **User Context**: User information is available in background threads
3. **Logging**: Structured logging with consistent correlation IDs
4. **Debugging**: Easier to trace requests through async operations
5. **Security**: User context is preserved for authorization checks

## Integration with Existing Code

The thread configuration is automatically applied when you use:
- `@Async` annotations (if configured with the custom executor)
- `ThreadContextAsyncExecutor` methods
- Any custom async operations using the configured task executor

## Example Service

See `ThreadContextExampleService` for a complete example of how to use these components.
