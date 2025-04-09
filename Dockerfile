# Use a Java base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the source code
COPY src/ /app/src/
COPY tests/ /app/tests/

# Create output directory
RUN mkdir -p /app/out/production/calculator

# Compile the application
RUN javac -d /app/out/production/calculator $(find /app/src -name "*.java")

# Set the classpath and entrypoint
ENTRYPOINT ["java", "-cp", "/app/out/production/calculator", "calculator.Main"]