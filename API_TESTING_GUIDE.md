# Spring Boot Microservices - API Testing Guide

This guide provides a complete flow of API testing for the Spring Boot microservices project with JWT authentication, centralized logging, and inter-service communication.

## Prerequisites

1. **Start the project:**
   ```bash
   docker compose up -d
   ```

2. **Wait for all services to be healthy:**
   ```bash
   docker compose ps
   ```

3. **Verify services are running:**
   - Account Service: http://localhost:8088
   - Product Service: http://localhost:8089
   - Elasticsearch: http://localhost:9200
   - Kibana: http://localhost:5601 (admin/admin)
   - Kafdrop: http://localhost:9000

## API Testing Flow

### 1. User Registration

**Register a new user with default ROLE_USER:**
```bash
curl -X POST http://localhost:8088/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123",
    "roles": []
  }'
```

**Expected Response:**
```json
{
  "message": "User registered successfully!"
}
```

**Register a user with specific role (ROLE_ADMIN):**
```bash
curl -X POST http://localhost:8088/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin_user",
    "email": "admin@example.com",
    "password": "password123",
    "roles": ["admin"]
  }'
```

**Note:** The system only supports `ROLE_USER` and `ROLE_ADMIN`. The `ROLE_MODERATOR` role has been removed. If no roles are provided or an unknown role is specified, the user will default to `ROLE_USER`.

### 2. User Authentication

**Login to get JWT token:**
```bash
curl -X POST http://localhost:8088/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "id": 10,
  "username": "john_doe",
    "email": "john@example.com",
    "roles": ["ROLE_USER"]
}
```

**Save the token for subsequent requests:**
```bash
# Extract token from response (example)
TOKEN="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huX2RvZSIsInVzZXJJZCI6IjEwIiwiaWF0IjoxNzU3NjAzNzg0LCJleHAiOjE3NTc2OTAxODR9.gPQyt1j0mNUqJTkrx0c6e2RJw-JP01DMMafMX1v497s"
```

### 3. User Profile Management

**Get current user profile (requires JWT):**
```bash
curl -X GET http://localhost:8088/api/user/me \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response:**
```json
{
  "id": 10,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

**Get user by ID (public endpoint, no JWT required):**
```bash
curl -X GET http://localhost:8088/api/user/10
```

**Expected Response:**
```json
{
  "id": 10,
  "username": "john_doe",
  "email": "john@example.com",
  "roles": ["ROLE_USER"]
}
```

### 4. Product Search API (Public Access)

**The search API is public and doesn't require JWT authentication.**

**Search Parameters:**
- `id`: Search by product ID
- `name`: Search by product name (case-insensitive, partial match)
- `description`: Search by description (case-insensitive, partial match)
- `creatorId`: Search by user ID who created the product
- `minPrice`: Minimum price filter
- `maxPrice`: Maximum price filter
- `page`: Page number (default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Sort field (default: createdAt)
- `sortDirection`: Sort direction - asc/desc (default: desc)

**Basic search examples:**
```bash
# Search all products
curl -X GET "http://localhost:8089/api/product/search"

# Search by name
curl -X GET "http://localhost:8089/api/product/search?name=Sample"

# Search by user
curl -X GET "http://localhost:8089/api/product/search?creatorId=1"

# Search with pagination
curl -X GET "http://localhost:8089/api/product/search?page=0&size=5"

# Search with sorting
curl -X GET "http://localhost:8089/api/product/search?sortBy=name&sortDirection=asc"

# Combined search
curl -X GET "http://localhost:8089/api/product/search?name=Product&creatorId=1&page=0&size=2&sortBy=name&sortDirection=asc"
```

### 5. Product Management (Inter-Service Communication)

**Get products by user ID (tests Feign client communication):**
```bash
# Test with existing user (userId=1 has seeded products)
curl -X GET "http://localhost:8089/api/product?userId=1"
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "name": "Sample Product A",
    "description": null,
    "price": null,
    "creatorId": 1,
    "createdAt": null,
    "updatedAt": null
  },
  {
    "id": 2,
    "name": "Sample Product B",
    "description": null,
    "price": null,
    "creatorId": 1,
    "createdAt": null,
    "updatedAt": null
  }
]
```

**Test with non-existent user (should return 404):**
```bash
curl -X GET "http://localhost:8089/api/product?userId=100"
```

**Expected Response:**
```json
{
  "statusCode": 404,
  "messages": ["User not found"],
  "errorCode": null
}
```

**Search products (public endpoint, no JWT required):**
```bash
# Search by name
curl -X GET "http://localhost:8089/api/product/search?name=Sample"

# Search by price range
curl -X GET "http://localhost:8089/api/product/search?minPrice=50&maxPrice=200"

# Search by user ID
curl -X GET "http://localhost:8089/api/product/search?creatorId=1"

# Search by description
curl -X GET "http://localhost:8089/api/product/search?description=testing"

# Search by ID
curl -X GET "http://localhost:8089/api/product/search?id=1"

# Combined search with pagination
curl -X GET "http://localhost:8089/api/product/search?name=Product&minPrice=100&page=0&size=5&sortBy=price&sortDirection=asc"
```

**Expected Response (paginated):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Sample Product A",
      "description": null,
      "price": null,
      "creatorId": 1,
      "createdAt": null,
      "updatedAt": null
    }
  ],
  "pageable": {
    "sort": [
      {
        "direction": "ASC",
        "property": "name",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true,
        "descending": false
      }
    ],
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 1,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 2,
  "totalPages": 2,
  "last": false,
  "first": true,
  "numberOfElements": 1
}
```

**Create a new product (requires ADMIN JWT only):**
```bash
curl -X POST http://localhost:8089/api/product \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d '{
    "name": "My New Product",
    "description": "A product created via API",
    "price": 199.99
  }'
```

**Expected Response:**
```json
{
  "id": 3,
  "name": "My New Product",
  "description": "A product created via API",
  "price": 199.99,
  "creatorId": 2,
  "createdAt": "2024-01-01T12:00:00",
  "updatedAt": "2024-01-01T12:00:00"
}
```

**Update a product (requires ADMIN JWT or be the creator):**
```bash
curl -X PATCH http://localhost:8089/api/product/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Updated Product Name",
    "price": 299.99
  }'
```

**Expected Response:**
```json
{
  "id": 1,
  "name": "Updated Product Name",
  "description": null,
  "price": 299.99,
  "creatorId": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T12:30:00"
}
```

**Note:** 
- Only users with `ROLE_ADMIN` can create products
- Users can update products they created OR users with `ROLE_ADMIN` can update any product
- Both create and update operations publish Kafka events for audit/logging

### 6. Error Handling Tests

**Test validation errors (signup with invalid data):**
```bash
curl -X POST http://localhost:8088/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "ab",
    "email": "",
    "password": "123"
  }'
```

**Expected Response:**
```json
{
  "statusCode": 400,
  "messages": [
    "username: size must be between 3 and 20",
    "password: size must be between 6 and 40",
    "email: must not be blank"
  ],
  "errorCode": null
}
```

**Test unauthorized access (without JWT):**
```bash
curl -X GET http://localhost:8088/api/user/me
```

**Expected Response:**
```json
{
  "statusCode": 401,
  "messages": ["Unauthorized"],
  "errorCode": null
}
```

**Test forbidden access (wrong role):**
```bash
# Try to create product with USER role (only ADMIN can create)
curl -X POST http://localhost:8089/api/product \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Unauthorized Product",
    "description": "This should fail",
    "price": 99.99
  }'
```

**Expected Response:**
```json
{
  "statusCode": 403,
  "messages": ["Access Denied"],
  "errorCode": null
}
```

### 7. Kafka Integration Tests

**Check Kafka topics and messages:**
1. Open Kafdrop: http://localhost:9000
2. Navigate to Topics
3. Look for these topics:
   - `user-events`: User registration/login events
   - `product-events`: Product creation/update events
4. Monitor message flow during API calls:
   - Create/update products to see product events
   - Register/login users to see user events

### 8. Centralized Logging Tests

**Check logs in Kibana:**
1. Open Kibana: http://localhost:5601
2. Login with admin/admin
3. Go to Discover
4. Create index pattern: `fluentd-*`
5. View logs with fields:
   - `container_name`: Docker container name
   - `correlation_id`: Unique correlation identifier for request tracing
   - `message`: Log message content
   - `level`: Log level (INFO, WARN, ERROR)
   - `logger_name`: Java logger name

**Search for specific logs:**
```bash
# In Kibana Discover, use these filters:
container_name: "account-service-app"
correlation_id: "specific-correlation-id"
message: "User registered successfully"
level: "ERROR"
logger_name: "com.demo.service.ProductService"
```

## Complete Testing Script

Here's a complete bash script to test all endpoints:

```bash
#!/bin/bash

echo "=== Spring Boot Microservices API Testing ==="

# 1. Register user
echo "1. Registering user..."
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8088/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "email": "test@example.com",
    "password": "password123",
    "roles": []
  }')
echo "Register response: $REGISTER_RESPONSE"

# 2. Login
echo "2. Logging in..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8088/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_user",
    "password": "password123"
  }')
echo "Login response: $LOGIN_RESPONSE"

# Extract token
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Extracted token: $TOKEN"

# 3. Get user profile
echo "3. Getting user profile..."
curl -s -X GET http://localhost:8088/api/user/me \
  -H "Authorization: Bearer $TOKEN" | jq '.'

# 4. Get user by ID
echo "4. Getting user by ID..."
curl -s -X GET http://localhost:8088/api/user/1 | jq '.'

# 5. Get products
echo "5. Getting products for user 1..."
curl -s -X GET "http://localhost:8089/api/product?userId=1" | jq '.'

# 6. Search products (public endpoint)
echo "6. Searching products by name..."
curl -s -X GET "http://localhost:8089/api/product/search?name=Sample"

# 7. Search products by price range
echo "7. Searching products by price range..."
curl -s -X GET "http://localhost:8089/api/product/search?minPrice=50&maxPrice=200"

# 8. Search products with pagination
echo "8. Searching products with pagination..."
curl -s -X GET "http://localhost:8089/api/product/search?page=0&size=1&sortBy=name&sortDirection=asc"

# 9. Test error case
echo "9. Testing error case (user not found)..."
curl -s -X GET "http://localhost:8089/api/product?userId=999"

echo "=== Testing Complete ==="
```

## Monitoring and Debugging

### Docker Logs
```bash
# View all service logs
docker compose logs -f

# View specific service logs
docker compose logs -f account-service-app
docker compose logs -f product-service-app
docker compose logs -f kafka
```

### Health Checks
```bash
# Check service health
docker compose ps

# Check individual service health
curl http://localhost:8088/actuator/health
curl http://localhost:8089/actuator/health
```

### Database Access
```bash
# Access PostgreSQL databases
docker exec -it spring-boot-microservice-account-service-db-1 psql -U postgres -d account-service
docker exec -it spring-boot-microservice-product-service-db-1 psql -U postgres -d product-service

# List tables
\dt

# Query products
SELECT * FROM product;

# Query users
SELECT * FROM users;
```

## Troubleshooting

### Common Issues

1. **Connection Refused**: Services not ready yet
   ```bash
   docker compose ps
   # Wait for all services to be "Up"
   ```

2. **JWT Token Expired**: Re-login to get new token
   ```bash
   # Token expires in 24 hours by default
   # Re-run login command
   ```

3. **403 Access Denied**: Check user roles
   ```bash
   # Verify user has correct role for endpoint
   # Only ROLE_ADMIN can create products
   # Users can only update their own products (unless they're ADMIN)
   # Check JWT token payload
   ```

4. **500 Internal Server Error**: Check service logs
   ```bash
   docker compose logs -f [service-name]
   ```

### Debug Mode
To enable debug mode for remote debugging:
1. Update `docker-compose.yml` with debug ports
2. Attach debugger to localhost:5005 (account-service) or localhost:5006 (product-service)
3. Set breakpoints in IDE
4. Make API calls to trigger breakpoints

## Architecture Overview

This project demonstrates:
- **Multi-module Maven architecture**
- **Microservices communication** (Feign client)
- **JWT authentication and authorization** (ROLE_USER, ROLE_ADMIN)
- **Centralized logging** (Fluentd + Elasticsearch + Kibana)
- **Message queuing** (Kafka) with event-driven architecture
- **Container orchestration** (Docker Compose)
- **Database persistence** (PostgreSQL)
- **Exception handling** and **API standardization**
- **Public search APIs** with pagination and filtering
- **Role-based access control** for product management

## Next Steps

1. **Add more business logic** to services
2. **Implement additional security features**
3. **Add more inter-service communication patterns**
4. **Implement circuit breakers** (Resilience4j)
5. **Add API documentation** (Swagger/OpenAPI)
6. **Implement caching** (Redis)
7. **Add monitoring** (Prometheus + Grafana)
