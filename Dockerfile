# Use Maven image with Eclipse Temurin JDK 21 to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

RUN chmod +x mvnw

# Build the project and skip tests
RUN ./mvnw clean install -DskipTests

# Use a lightweight JDK 21 image to run the jar
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
