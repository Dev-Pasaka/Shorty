# Stage 1: Build the application
FROM gradle:7.2.0-jdk11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files for dependency resolution
COPY build.gradle.kts settings.gradle.kts /app/
COPY src /app/src

# Resolve dependencies (caching them)
RUN gradle clean build --no-daemon

# Stage 2: Create the final Docker image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container from the build stage
COPY --from=build /app/build/libs/Shorty.jar /app/

# Declare and set environment variables using ARG and ENV
ARG API_VERSION
ARG BASE_URL
ARG LOGGER_NAME
ARG SERVER_PORT

ENV API_VERSION=${API_VERSION}
ENV BASE_URL=${BASE_URL}
ENV LOGGER_NAME=${LOGGER_NAME}
ENV SERVER_PORT=${SERVER_PORT}

# Expose the port your application runs on
EXPOSE 8088

# Command to run the application
CMD ["java", "-jar", "Shorty.jar"]

