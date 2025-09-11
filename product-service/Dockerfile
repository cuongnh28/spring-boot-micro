FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom files first to leverage Docker layer caching for dependencies
COPY pom.xml ./
COPY commons/pom.xml commons/pom.xml
COPY account-service/pom.xml account-service/pom.xml
COPY product-service/pom.xml product-service/pom.xml

RUN mvn -q -DskipTests dependency:go-offline

# Copy full source and build only the product-service (and its dependencies)
COPY . .
RUN mvn -q -pl product-service -am -DskipTests package

FROM openjdk:17
WORKDIR /app

LABEL authors="cuongnh"

COPY --from=build /app/product-service/target/*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","/app/app.jar"]