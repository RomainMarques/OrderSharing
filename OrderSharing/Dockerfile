# Use a base image with OpenJDK to run Java applications
FROM openjdk:17-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Copy the Maven configuration files
COPY pom.xml .

# Copy the source code
COPY src src

# Ensure that the Maven wrapper script is executable
RUN chmod +x mvnw
# clean up the file
# RUN sed -i 's/\r$//' mvnw
# run with the SH path
# RUN /bin/sh mvnw dependency:resolve

# Build the application
RUN ./mvnw package -DskipTests

# Use a lightweight base image for running the application
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
