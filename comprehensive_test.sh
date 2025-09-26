#!/bin/bash

# Comprehensive Microservice Verification Script
# This script tests all aspects of the microservice architecture

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# JWT tokens
ADMIN_JWT="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRvIiwidXNlcklkIjoiMSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzU4Mjc2MTMxLCJleHAiOjE3NTgzNjI1MzF9.RBzVuUDRE_te6LimHsIlVe5sKFeD-YPvVkI9e4zufUM"
USER_JWT="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRvMSIsInVzZXJJZCI6IjIiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNzU4Mjc2MTcwLCJleHAiOjE3NTgzNjI1NzB9.4RNTyEm87EVS-RPZt00Oj6ijZuGSjBvwgHQXZ2XJHlY"

echo -e "${BLUE}=== COMPREHENSIVE MICROSERVICE VERIFICATION ===${NC}"
echo "Testing all APIs, Kafka, Circuit Breaker, Async, and Logging"
echo ""

# Function to print test results
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ $2${NC}"
    else
        echo -e "${RED}✗ $2${NC}"
    fi
}

# Function to wait for service
wait_for_service() {
    local url=$1
    local name=$2
    local max_attempts=30
    local attempt=1
    
    echo -e "${YELLOW}Waiting for $name to be ready...${NC}"
    while [ $attempt -le $max_attempts ]; do
        if curl -s "$url" > /dev/null 2>&1; then
            echo -e "${GREEN}✓ $name is ready${NC}"
            return 0
        fi
        echo -n "."
        sleep 2
        attempt=$((attempt + 1))
    done
    echo -e "${RED}✗ $name failed to start within timeout${NC}"
    return 1
}

# 1. Check Infrastructure
echo -e "${BLUE}=== 1. INFRASTRUCTURE CHECK ===${NC}"
wait_for_service "http://localhost:8088/actuator/health" "Account Service"
wait_for_service "http://localhost:8089/actuator/health" "Product Service"
wait_for_service "http://localhost:8085" "Kafdrop"
wait_for_service "http://localhost:9200" "Elasticsearch"

# 2. Test Authentication Flow
echo -e "${BLUE}=== 2. AUTHENTICATION FLOW ===${NC}"

# Test login with existing user
echo "Testing login..."
LOGIN_RESPONSE=$(curl -s -X POST "http://localhost:8088/api/auth/signin" \
    -H "Content-Type: application/json" \
    -d '{"username":"vito","password":"123456"}' 2>/dev/null)

if echo "$LOGIN_RESPONSE" | jq -e '.accessToken' > /dev/null 2>&1; then
    print_result 0 "Login successful"
    NEW_JWT=$(echo "$LOGIN_RESPONSE" | jq -r '.accessToken')
    echo "New JWT obtained: ${NEW_JWT:0:50}..."
else
    print_result 1 "Login failed"
    echo "Response: $LOGIN_RESPONSE"
fi

# 3. Test Product APIs
echo -e "${BLUE}=== 3. PRODUCT API TESTS ===${NC}"

# Create a product
echo "Creating product..."
CREATE_RESPONSE=$(curl -s -X POST "http://localhost:8089/api/product" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $ADMIN_JWT" \
    -d '{"name":"Test Product","description":"Test Description","price":99.99,"creatorId":1}' 2>/dev/null)

if echo "$CREATE_RESPONSE" | jq -e '.id' > /dev/null 2>&1; then
    print_result 0 "Product creation successful"
    PRODUCT_ID=$(echo "$CREATE_RESPONSE" | jq -r '.id')
    echo "Created product ID: $PRODUCT_ID"
else
    print_result 1 "Product creation failed"
    echo "Response: $CREATE_RESPONSE"
fi

# Update the product
echo "Updating product..."
UPDATE_RESPONSE=$(curl -s -X PUT "http://localhost:8089/api/product/$PRODUCT_ID" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $ADMIN_JWT" \
    -d '{"name":"Updated Test Product","description":"Updated Description","price":149.99}' 2>/dev/null)

if echo "$UPDATE_RESPONSE" | jq -e '.id' > /dev/null 2>&1; then
    print_result 0 "Product update successful"
else
    print_result 1 "Product update failed"
    echo "Response: $UPDATE_RESPONSE"
fi

# Get products by user
echo "Getting products by user..."
GET_RESPONSE=$(curl -s "http://localhost:8089/api/product?userId=1" \
    -H "Authorization: Bearer $ADMIN_JWT" 2>/dev/null)

if echo "$GET_RESPONSE" | jq -e '.[0].id' > /dev/null 2>&1; then
    print_result 0 "Get products by user successful"
else
    print_result 1 "Get products by user failed"
    echo "Response: $GET_RESPONSE"
fi

# 4. Test Kafka Events
echo -e "${BLUE}=== 4. KAFKA EVENT TESTS ===${NC}"

# Check Kafka topics
echo "Checking Kafka topics..."
KAFKA_TOPICS=$(curl -s "http://localhost:8085/topic" 2>/dev/null)
if echo "$KAFKA_TOPICS" | grep -q "product-events"; then
    print_result 0 "Product events topic exists"
else
    print_result 1 "Product events topic not found"
fi

# 5. Test Async Endpoints
echo -e "${BLUE}=== 5. ASYNC ENDPOINT TESTS ===${NC}"

# Test async endpoint in product service
echo "Testing async endpoint..."
ASYNC_RESPONSE=$(curl -s -X POST "http://localhost:8089/api/async/test" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $ADMIN_JWT" \
    -d '{"message":"Async test message"}' 2>/dev/null)

if echo "$ASYNC_RESPONSE" | jq -e '.message' > /dev/null 2>&1; then
    print_result 0 "Async endpoint successful"
else
    print_result 1 "Async endpoint failed"
    echo "Response: $ASYNC_RESPONSE"
fi

# 6. Test Circuit Breaker
echo -e "${BLUE}=== 6. CIRCUIT BREAKER TESTS ===${NC}"

# First, test normal operation
echo "Testing normal operation (account-service up)..."
NORMAL_RESPONSE=$(curl -s "http://localhost:8089/api/product?userId=1" \
    -H "Authorization: Bearer $ADMIN_JWT" 2>/dev/null)

if echo "$NORMAL_RESPONSE" | jq -e '.[0].id' > /dev/null 2>&1; then
    print_result 0 "Normal operation successful"
else
    print_result 1 "Normal operation failed"
fi

# Stop account-service to test circuit breaker
echo "Stopping account-service to test circuit breaker..."
pkill -f "account-service.*jar" || true
sleep 5

# Test circuit breaker fallback
echo "Testing circuit breaker fallback..."
FALLBACK_RESPONSE=$(curl -s "http://localhost:8089/api/product?userId=1" \
    -H "Authorization: Bearer $ADMIN_JWT" 2>/dev/null)

if echo "$FALLBACK_RESPONSE" | jq -e '.[]' > /dev/null 2>&1; then
    print_result 0 "Circuit breaker fallback successful"
else
    print_result 1 "Circuit breaker fallback failed"
    echo "Response: $FALLBACK_RESPONSE"
fi

# Restart account-service
echo "Restarting account-service..."
cd /home/cuongnh/Working/spring-boot-microservice
DB_URL=jdbc:postgresql://localhost:5432/account-service java -jar account-service/target/account-service-1.0-SNAPSHOT.jar &
sleep 15

# 7. Test Logging
echo -e "${BLUE}=== 7. LOGGING TESTS ===${NC}"

# Check if logs are being sent to Elasticsearch
echo "Checking Elasticsearch logs..."
ES_LOGS=$(curl -s "http://localhost:9200/_search?q=*&size=1" 2>/dev/null)
if echo "$ES_LOGS" | jq -e '.hits.total.value' > /dev/null 2>&1; then
    print_result 0 "Elasticsearch logging working"
else
    print_result 1 "Elasticsearch logging not working"
fi

# 8. Test User Management
echo -e "${BLUE}=== 8. USER MANAGEMENT TESTS ===${NC}"

# Get user info
echo "Getting user info..."
USER_RESPONSE=$(curl -s "http://localhost:8088/api/user/1" \
    -H "Authorization: Bearer $ADMIN_JWT" 2>/dev/null)

if echo "$USER_RESPONSE" | jq -e '.id' > /dev/null 2>&1; then
    print_result 0 "Get user info successful"
else
    print_result 1 "Get user info failed"
    echo "Response: $USER_RESPONSE"
fi

# 9. Test Security
echo -e "${BLUE}=== 9. SECURITY TESTS ===${NC}"

# Test unauthorized access
echo "Testing unauthorized access..."
UNAUTH_RESPONSE=$(curl -s -w "%{http_code}" "http://localhost:8089/api/product" 2>/dev/null)
if [[ "$UNAUTH_RESPONSE" == *"401"* ]]; then
    print_result 0 "Unauthorized access properly blocked"
else
    print_result 1 "Unauthorized access not blocked"
fi

# Test role-based access
echo "Testing role-based access..."
ROLE_RESPONSE=$(curl -s -X POST "http://localhost:8089/api/product" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $USER_JWT" \
    -d '{"name":"Test Product","description":"Test Description","price":99.99,"creatorId":2}' 2>/dev/null)

if echo "$ROLE_RESPONSE" | jq -e '.id' > /dev/null 2>&1; then
    print_result 0 "Role-based access working"
else
    print_result 1 "Role-based access failed"
    echo "Response: $ROLE_RESPONSE"
fi

echo -e "${BLUE}=== VERIFICATION COMPLETE ===${NC}"
echo "All tests completed. Check the results above."

