## Spring Boot Microservices - Build & Deploy Guide

This repository is a Maven multi-module project with two services and a shared `commons` module:

- `commons`: shared code and configs
- `account-service`: Spring Boot application (port 8088)
- `product-service`: Spring Boot application (port 8089)

Docker Compose services of interest:

- `account-service-app` (container + image for account-service)
- `product-service-app` (container + image for product-service)
- Infra: `zookeeper`, `kafka`, `elasticsearch`, `kibana`, `fluentd`, `account-service-db`, `product-service-db`

### Prerequisites

- Java 17, Maven 3.9+
- Docker and Docker Compose

### Build everything (after changing commons or multiple modules)

```bash
cd /home/cuongnh/Working/spring-boot-microservice
mvn -DskipTests clean install
docker compose build account-service-app product-service-app
docker compose up -d --force-recreate account-service-app product-service-app
```

### After updating only account-service

```bash
mvn -pl account-service -am -DskipTests clean install
docker compose build account-service-app
docker compose up -d --force-recreate account-service-app
```

### After updating only product-service

```bash
mvn -pl product-service -am -DskipTests clean install
docker compose build product-service-app
docker compose up -d --force-recreate product-service-app
```

### First-time or infra startup

Bring up dependencies and databases before the apps:

```bash
docker compose up -d zookeeper kafka elasticsearch kibana fluentd account-service-db product-service-db
```

Then start applications:

```bash
docker compose up -d account-service-app product-service-app
```

### Logs

```bash
docker compose logs -f account-service-app
docker compose logs -f product-service-app
```

### Force fresh image rebuild (avoid stale JARs)

```bash
docker compose build --no-cache account-service-app product-service-app
```

### Clean restart a container

```bash
docker compose stop account-service-app && docker compose rm -f account-service-app
docker compose up -d account-service-app
```

### Service URLs

- Account Service: http://localhost:8088
- Product Service: http://localhost:8089
- Kibana: http://localhost:5601 (user: `elastic`, pass: `elastic`)
- Kafdrop (if enabled): http://localhost:8085

### Notes

- The root `docker-compose.yml` defines app services as `account-service-app` and `product-service-app`. Use these names with Docker commands.
- Running `mvn` locally before building Docker images speeds up builds and validates compilation, but each service `Dockerfile` can also build inside the image if needed.
- If you see warnings about compose `version` or `ZOOKEEPER_CLIENT_PORT`, they are benign given current config; infra services set necessary defaults internally.


