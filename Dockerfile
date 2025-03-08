# Use a base image that has Java installed
FROM openjdk:17-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/TrainProject-0.0.1-SNAPSHOT.jar /app/TrainProject-0.0.1-SNAPSHOT.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "TrainProject-0.0.1-SNAPSHOT.jar"]
