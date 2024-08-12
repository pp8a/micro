# Use OpenJDK image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/discovery-server-0.0.1-SNAPSHOT.jar /app/app.jar

# Set the environment variable for JVM options
ENV JAVA_OPTS="-XX:+EnableDynamicAgentLoading"

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/app.jar"]