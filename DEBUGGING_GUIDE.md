# Debugging Guide for Spring Boot Microservices

This guide explains how to enable and use debugging for your Spring Boot microservices running in Docker containers.

## Quick Start

### Option 1: Debug with Override File (Recommended)
```bash
# Start all services with debug mode enabled
docker-compose -f docker-compose.yml -f docker-compose.debug.yml up --build

# Or start only specific services
docker-compose -f docker-compose.yml -f docker-compose.debug.yml up account-service-app product-service-app
```

### Option 2: Use Modified docker-compose.yml
```bash
# Start all services (debug is already enabled in the main compose file)
docker-compose up --build
```

## Debug Ports

- **Account Service**: Port `5005` (localhost:5005)
- **Product Service**: Port `5006` (localhost:5006)

## IDE Configuration

### IntelliJ IDEA

1. **Go to Run/Debug Configurations**
   - Click on "Edit Configurations..." or go to Run → Edit Configurations

2. **Add Remote Debug Configuration**
   - Click the "+" button and select "Remote JVM Debug"
   - Name it "Account Service Debug" or "Product Service Debug"

3. **Configure Connection**
   - **Host**: `localhost`
   - **Port**: `5005` (for Account Service) or `5006` (for Product Service)
   - **Module classpath**: Select your project module
   - **Use module classpath**: Check this option

4. **Save and Connect**
   - Click "Apply" and "OK"
   - Start the debug configuration
   - Set breakpoints in your code
   - The debugger will connect when the application starts

### Visual Studio Code

1. **Create launch.json**
   - Go to Run and Debug view (Ctrl+Shift+D)
   - Click "create a launch.json file"
   - Select "Java" as the environment

2. **Add Remote Debug Configuration**
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Debug Account Service",
         "request": "attach",
         "hostName": "localhost",
         "port": 5005
       },
       {
         "type": "java",
         "name": "Debug Product Service",
         "request": "attach",
         "hostName": "localhost",
         "port": 5006
       }
     ]
   }
   ```

3. **Start Debugging**
   - Select the debug configuration from the dropdown
   - Click the play button or press F5
   - Set breakpoints in your code

### Eclipse

1. **Create Remote Debug Configuration**
   - Go to Run → Debug Configurations
   - Right-click "Remote Java Application" → New
   - Name: "Account Service Debug" or "Product Service Debug"

2. **Configure Connection**
   - **Project**: Select your project
   - **Host**: `localhost`
   - **Port**: `5005` (Account Service) or `5006` (Product Service)

3. **Start Debugging**
   - Click "Debug"
   - Set breakpoints in your code

## Debug Features Enabled

### JVM Debug Agent
- **Transport**: dt_socket
- **Server**: y (accepts incoming connections)
- **Suspend**: n (doesn't wait for debugger to start)
- **Address**: *:5005 (Account Service) / *:5006 (Product Service)

### Enhanced Logging
- **Spring Framework**: DEBUG level
- **Hibernate**: DEBUG level
- **Application**: DEBUG level
- **Profile**: debug

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Check if ports are in use
   netstat -an | findstr :5005
   netstat -an | findstr :5006
   
   # Kill processes using the ports
   taskkill /PID <process_id> /F
   ```

2. **Debugger Won't Connect**
   - Ensure Docker containers are running
   - Check if debug ports are exposed: `docker ps`
   - Verify firewall settings allow connections to ports 5005/5006

3. **Application Won't Start**
   - Check Docker logs: `docker logs account-service-app`
   - Verify debug parameters in Dockerfile
   - Ensure all dependencies are available

### Useful Commands

```bash
# Check container status
docker ps

# View logs
docker logs account-service-app
docker logs product-service-app

# Check if debug ports are listening
docker port account-service-app
docker port product-service-app

# Rebuild and restart with debug
docker-compose -f docker-compose.yml -f docker-compose.debug.yml down
docker-compose -f docker-compose.yml -f docker-compose.debug.yml up --build

# Start only specific service for debugging
docker-compose -f docker-compose.yml -f docker-compose.debug.yml up account-service-app
```

## Debugging Tips

1. **Set Breakpoints Early**: Set breakpoints before starting the debugger
2. **Use Conditional Breakpoints**: Set conditions for breakpoints to avoid unnecessary stops
3. **Monitor Logs**: Keep Docker logs open in a separate terminal
4. **Step Through Code**: Use Step Over (F8), Step Into (F7), and Step Out (Shift+F8)
5. **Inspect Variables**: Use the Variables panel to examine object states
6. **Evaluate Expressions**: Use the Expression evaluator for quick code execution

## Performance Considerations

- Debug mode adds overhead to the JVM
- Use debug mode only when needed
- Consider using debug profiles for different environments
- Monitor memory usage during debugging sessions

## Security Notes

- Debug ports are exposed to localhost only
- Don't expose debug ports in production
- Use debug mode only in development environments
- Consider using SSH tunneling for remote debugging if needed
