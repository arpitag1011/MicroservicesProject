# Step 1: Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy and build the app
COPY target/UserService-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8081
