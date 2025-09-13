# Docker & Microservices Debugging Commands Guide

This guide contains all the essential commands for debugging Docker containers, microservices, and system issues that we've used throughout the project setup and troubleshooting.

## Table of Contents
1. [Docker Container Management](#docker-container-management)
2. [Docker Compose Commands](#docker-compose-commands)
3. [Log Analysis](#log-analysis)
4. [Network Debugging](#network-debugging)
5. [File System Operations](#file-system-operations)
6. [Process and System Monitoring](#process-and-system-monitoring)
7. [Database Debugging](#database-debugging)
8. [Application Debugging](#application-debugging)
9. [Build and Deployment](#build-and-deployment)
10. [Quick Troubleshooting Workflows](#quick-troubleshooting-workflows)

## Docker Container Management

### Container Status and Information
```bash
# List all containers (running and stopped)
docker ps -a

# List only running containers
docker ps

# Show container resource usage
docker stats

# Show detailed container information
docker inspect <container_name_or_id>

# Show container processes
docker top <container_name_or_id>

# Show container port mappings
docker port <container_name_or_id>
```

### Container Lifecycle
```bash
# Start a container
docker start <container_name_or_id>

# Stop a container
docker stop <container_name_or_id>

# Restart a container
docker restart <container_name_or_id>

# Remove a container
docker rm <container_name_or_id>

# Remove a running container (force)
docker rm -f <container_name_or_id>

# Remove all stopped containers
docker container prune
```

### Container Execution
```bash
# Execute command in running container
docker exec -it <container_name> <command>

# Get shell access to container
docker exec -it <container_name> /bin/bash
docker exec -it <container_name> /bin/sh

# Execute command as specific user
docker exec -it --user root <container_name> /bin/bash
```

## Docker Compose Commands

### Service Management
```bash
# Start all services
docker compose up

# Start services in background
docker compose up -d

# Start specific services
docker compose up <service_name1> <service_name2>

# Stop all services
docker compose down

# Stop and remove volumes
docker compose down -v

# Stop and remove everything (containers, networks, volumes)
docker compose down --remove-orphans

# Restart specific service
docker compose restart <service_name>

# Scale a service
docker compose up -d --scale <service_name>=3
```

### Service Status and Logs
```bash
# Show service status
docker compose ps

# Show logs for all services
docker compose logs

# Show logs for specific service
docker compose logs <service_name>

# Follow logs in real-time
docker compose logs -f

# Follow logs for specific service
docker compose logs -f <service_name>

# Show last N lines of logs
docker compose logs --tail=100 <service_name>

# Show logs with timestamps
docker compose logs -t <service_name>
```

### Build and Rebuild
```bash
# Build all services
docker compose build

# Build specific service
docker compose build <service_name>

# Build without cache
docker compose build --no-cache

# Build and start services
docker compose up --build

# Rebuild and restart specific service
docker compose up --build <service_name>
```

## Log Analysis

### Docker Logs
```bash
# View container logs
docker logs <container_name>

# Follow logs in real-time
docker logs -f <container_name>

# Show last N lines
docker logs --tail=50 <container_name>

# Show logs with timestamps
docker logs -t <container_name>

# Show logs since specific time
docker logs --since="2024-01-01T10:00:00" <container_name>

# Show logs until specific time
docker logs --until="2024-01-01T12:00:00" <container_name>
```

### Grep and Text Processing
```bash
# Search for specific text in logs
docker logs <container_name> | grep "ERROR"
docker logs <container_name> | grep -i "exception"

# Search with context (before and after lines)
docker logs <container_name> | grep -A 5 -B 5 "ERROR"

# Search for multiple patterns
docker logs <container_name> | grep -E "(ERROR|WARN|Exception)"

# Case-insensitive search
docker logs <container_name> | grep -i "kafka"

# Search and count occurrences
docker logs <container_name> | grep -c "ERROR"

# Search in multiple containers
docker compose logs | grep "Connection refused"
```

### Log File Analysis
```bash
# View log files directly
tail -f /var/log/docker.log
tail -f /var/lib/docker/containers/*/container.log

# Search in log files
grep -r "ERROR" /var/log/
grep -r "Exception" /var/lib/docker/containers/

# Monitor log files in real-time
tail -f /var/log/syslog | grep docker
```

## Network Debugging

### Docker Network Commands
```bash
# List Docker networks
docker network ls

# Inspect network
docker network inspect <network_name>

# Show network details
docker network inspect bridge

# Create custom network
docker network create <network_name>

# Connect container to network
docker network connect <network_name> <container_name>

# Disconnect container from network
docker network disconnect <network_name> <container_name>
```

### Network Connectivity Testing
```bash
# Test connectivity between containers
docker exec -it <container1> ping <container2>

# Test port connectivity
docker exec -it <container> telnet <host> <port>
docker exec -it <container> nc -zv <host> <port>

# Test HTTP connectivity
docker exec -it <container> curl -v http://<host>:<port>

# Check DNS resolution
docker exec -it <container> nslookup <hostname>
docker exec -it <container> dig <hostname>
```

### Port and Service Discovery
```bash
# Check what's listening on ports
netstat -tulpn | grep :8088
netstat -tulpn | grep :8089

# Check port usage
lsof -i :8088
lsof -i :8089

# Test local port connectivity
telnet localhost 8088
curl -v http://localhost:8088/actuator/health
```

## File System Operations

### File and Directory Operations
```bash
# List files and directories
ls -la

# Find files by name
find . -name "*.java"
find . -name "Dockerfile"

# Find files by content
grep -r "spring.kafka" .
grep -r "localhost" .

# Find large files
find . -type f -size +100M

# Show disk usage
du -sh *
du -h --max-depth=1

# Show file permissions
ls -la <file_or_directory>
```

### File Content Analysis
```bash
# View file content
cat <filename>
less <filename>
more <filename>

# View first/last lines
head -n 20 <filename>
tail -n 20 <filename>

# Search in files
grep -n "pattern" <filename>
grep -r "pattern" <directory>

# Count lines, words, characters
wc -l <filename>
wc -w <filename>
wc -c <filename>
```

### File Operations
```bash
# Copy files
cp <source> <destination>
cp -r <source_directory> <destination_directory>

# Move/rename files
mv <old_name> <new_name>

# Remove files
rm <filename>
rm -rf <directory>

# Create directories
mkdir -p <directory_path>

# Change permissions
chmod 755 <filename>
chmod +x <filename>
```

## Process and System Monitoring

### Process Management
```bash
# Show running processes
ps aux
ps aux | grep java
ps aux | grep docker

# Show process tree
pstree
pstree -p

# Show process by PID
ps -p <pid>

# Kill process
kill <pid>
kill -9 <pid>

# Show process resource usage
top
htop
```

### System Resources
```bash
# Show memory usage
free -h
cat /proc/meminfo

# Show disk usage
df -h
lsblk

# Show CPU information
cat /proc/cpuinfo
lscpu

# Show system load
uptime
cat /proc/loadavg
```

### System Logs
```bash
# View system logs
journalctl -f
journalctl -u docker.service
journalctl --since "1 hour ago"

# View kernel logs
dmesg | tail
dmesg | grep -i error

# View boot logs
journalctl -b
```

## Database Debugging

### PostgreSQL Commands
```bash
# Connect to PostgreSQL container
docker exec -it <postgres_container> psql -U <username> -d <database>

# List databases
docker exec -it <postgres_container> psql -U postgres -c "\l"

# List tables
docker exec -it <postgres_container> psql -U postgres -d <database> -c "\dt"

# Show table structure
docker exec -it <postgres_container> psql -U postgres -d <database> -c "\d <table_name>"

# Execute SQL query
docker exec -it <postgres_container> psql -U postgres -d <database> -c "SELECT * FROM users;"

# Backup database
docker exec <postgres_container> pg_dump -U postgres <database> > backup.sql

# Restore database
docker exec -i <postgres_container> psql -U postgres <database> < backup.sql
```

### Database Connection Testing
```bash
# Test database connectivity
docker exec -it <app_container> telnet <db_host> 5432

# Check database logs
docker logs <postgres_container> | grep -i error
docker logs <postgres_container> | grep -i connection
```

## Application Debugging

### Java Application Debugging
```bash
# Check Java process
docker exec -it <container> jps
docker exec -it <container> ps aux | grep java

# Check JVM memory
docker exec -it <container> jstat -gc <pid>
docker exec -it <container> jmap -heap <pid>

# Check application logs
docker logs <container> | grep -i "started"
docker logs <container> | grep -i "error"
docker logs <container> | grep -i "exception"

# Check application properties
docker exec -it <container> cat /app/application.properties
```

### Spring Boot Actuator
```bash
# Health check
curl http://localhost:8088/actuator/health
curl http://localhost:8089/actuator/health

# Application info
curl http://localhost:8088/actuator/info

# Environment variables
curl http://localhost:8088/actuator/env

# Metrics
curl http://localhost:8088/actuator/metrics

# Thread dump
curl http://localhost:8088/actuator/threaddump
```

### Remote Debugging
```bash
# Check if debug port is open
netstat -tulpn | grep :5005
telnet localhost 5005

# Test debug connection
nc -zv localhost 5005
```

## Build and Deployment

### Docker Build Commands
```bash
# Build image
docker build -t <image_name> .

# Build with specific Dockerfile
docker build -f <dockerfile_path> -t <image_name> .

# Build without cache
docker build --no-cache -t <image_name> .

# Build with build args
docker build --build-arg <arg_name>=<arg_value> -t <image_name> .

# Show build history
docker history <image_name>
```

### Image Management
```bash
# List images
docker images
docker image ls

# Remove image
docker rmi <image_name>
docker rmi <image_id>

# Remove unused images
docker image prune

# Show image details
docker inspect <image_name>
```

### Maven Build Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Install to local repository
mvn install

# Skip tests
mvn package -DskipTests

# Clean install
mvn clean install

# Show dependency tree
mvn dependency:tree
```

## Quick Troubleshooting Workflows

### Service Won't Start
```bash
# 1. Check service status
docker compose ps

# 2. Check logs
docker compose logs <service_name>

# 3. Check if port is in use
netstat -tulpn | grep :<port>

# 4. Check container health
docker inspect <container_name> | grep -A 10 "Health"

# 5. Restart service
docker compose restart <service_name>
```

### Connection Issues
```bash
# 1. Check network connectivity
docker exec -it <container1> ping <container2>

# 2. Check DNS resolution
docker exec -it <container> nslookup <hostname>

# 3. Check port connectivity
docker exec -it <container> telnet <host> <port>

# 4. Check firewall
iptables -L
ufw status
```

### Performance Issues
```bash
# 1. Check resource usage
docker stats

# 2. Check system load
top
htop

# 3. Check disk space
df -h

# 4. Check memory usage
free -h

# 5. Check application logs for errors
docker logs <container> | grep -i error
```

### Database Issues
```bash
# 1. Check database container
docker logs <db_container>

# 2. Test connection
docker exec -it <app_container> telnet <db_host> 5432

# 3. Check database logs
docker exec -it <db_container> tail -f /var/log/postgresql/postgresql.log

# 4. Check database size
docker exec -it <db_container> psql -U postgres -c "SELECT pg_size_pretty(pg_database_size('dbname'));"
```

### Log Analysis Workflow
```bash
# 1. Get recent logs
docker logs --tail=100 <container>

# 2. Search for errors
docker logs <container> | grep -i error

# 3. Search with context
docker logs <container> | grep -A 5 -B 5 "exception"

# 4. Search for specific patterns
docker logs <container> | grep -E "(ERROR|WARN|Exception)"

# 5. Save logs to file
docker logs <container> > container_logs.txt
```

## Useful Aliases

Add these to your `~/.bashrc` or `~/.zshrc`:

```bash
# Docker aliases
alias dps='docker ps'
alias dpsa='docker ps -a'
alias dlogs='docker logs -f'
alias dexec='docker exec -it'
alias dcompose='docker compose'

# Docker Compose aliases
alias dc='docker compose'
alias dcu='docker compose up -d'
alias dcd='docker compose down'
alias dcl='docker compose logs -f'
alias dcb='docker compose build'
alias dcr='docker compose restart'

# Log analysis aliases
alias dlogserr='docker logs | grep -i error'
alias dlogsex='docker logs | grep -i exception'
alias dlogswarn='docker logs | grep -i warn'
```

## Emergency Commands

### When Everything is Broken
```bash
# Stop everything
docker compose down --remove-orphans

# Remove all containers
docker container prune -f

# Remove all images
docker image prune -a -f

# Remove all volumes
docker volume prune -f

# Remove all networks
docker network prune -f

# Clean everything
docker system prune -a -f --volumes

# Restart Docker service
sudo systemctl restart docker
```

### Quick Health Check
```bash
# Check all services
docker compose ps

# Check all logs for errors
docker compose logs | grep -i error

# Check resource usage
docker stats --no-stream

# Check disk space
df -h

# Check memory
free -h
```

This guide covers all the essential commands we've used throughout our troubleshooting sessions. Keep it handy for debugging microservices issues!

