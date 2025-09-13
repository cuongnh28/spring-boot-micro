# Circuit Breaker Configuration

This module provides circuit breaker functionality for Feign clients using Resilience4j.

## Features

- **Automatic Circuit Breaker**: Protects against cascading failures
- **Configurable Timeouts**: Customizable connection and read timeouts
- **Health Monitoring**: Circuit breaker status via actuator endpoints
- **Fallback Support**: Graceful degradation when services are unavailable

## Configuration

### 1. Add to your service's application.yml:

```yaml
# Enable circuit breaker for Feign
feign:
  circuitbreaker:
    enabled: true

# Resilience4j configuration
resilience4j:
  circuitbreaker:
    instances:
      feign:
        failureRateThreshold: 60
        waitDurationInOpenState: 60s
        slidingWindowSize: 20
        minimumNumberOfCalls: 10
```

### 2. Use in your Feign client:

```java
@FeignClient(
    name = "user-service",
    url = "http://user-service:8080",
    configuration = {CommonFeignConfig.class}
)
public interface UserFeignClient {
    
    @GetMapping("/api/users/{id}")
    User getUserById(@PathVariable Long id);
}
```

## Monitoring

Access circuit breaker status via actuator endpoints:

- **Health**: `GET /actuator/health`
- **Circuit Breakers**: `GET /actuator/circuitbreakers`
- **Metrics**: `GET /actuator/metrics`

## Circuit Breaker States

1. **CLOSED**: Normal operation, requests pass through
2. **OPEN**: Circuit is open, requests fail fast
3. **HALF_OPEN**: Testing if service is back online

## Configuration Parameters

- `failureRateThreshold`: Percentage of failures to open circuit (default: 60%)
- `waitDurationInOpenState`: How long to wait before trying again (default: 60s)
- `slidingWindowSize`: Number of calls to consider for failure rate (default: 20)
- `minimumNumberOfCalls`: Minimum calls before calculating failure rate (default: 10)
- `slowCallRateThreshold`: Percentage of slow calls to open circuit (default: 60%)
- `slowCallDurationThreshold`: Duration considered as slow call (default: 8s)


