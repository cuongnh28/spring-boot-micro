# Utility Classes

This package contains utility classes that provide common functionality across the microservices.

## AuthUtils

The `AuthUtils` class provides static methods for authentication-related operations. It extracts user information from the Spring Security context.

### Methods

- `getCurrentUserId()` - Gets the current authenticated user's ID
- `getCurrentUsername()` - Gets the current authenticated user's username  
- `isCurrentUserAdmin()` - Checks if the current user has admin role
- `isAuthenticated()` - Checks if the current user is authenticated
- `getCurrentAuthentication()` - Gets the current authentication object

### Usage Example

```java
import com.demo.util.AuthUtils;

// Get current user information
Long userId = AuthUtils.getCurrentUserId();
String username = AuthUtils.getCurrentUsername();
boolean isAdmin = AuthUtils.isCurrentUserAdmin();

// Check authentication status
if (AuthUtils.isAuthenticated()) {
    // User is authenticated
}
```

### Benefits

- **Reusability**: Can be used across all microservices
- **Consistency**: Standardized way to access authentication information
- **Maintainability**: Centralized authentication logic
- **Type Safety**: Proper exception handling for unauthenticated users
