# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project (including the build artifacts) into the container
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port on which the application will run
EXPOSE 8080

# Set the entry point to run the Spring Boot application
CMD ["java", "-jar", "target/Application.jar"]