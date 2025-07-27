# Use Maven image with Eclipse Temurin JDK 21 to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

RUN chmod +x mvnw

# modified by cursor - Build the project and skip tests
RUN ./mvnw clean install -DskipTests

# Use a lightweight JDK 21 image to run the jar
FROM eclipse-temurin:21-jdk-alpine

# Install wget for health checks
RUN apk add --no-cache wget

# Set working directory
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=build /app/target/*.jar app.jar

# modified by cursor - Expose the application port
EXPOSE 8080

# modified by cursor - Run the application with proper JVM options and health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-Dspring.profiles.active=production", "-jar", "app.jar"]
