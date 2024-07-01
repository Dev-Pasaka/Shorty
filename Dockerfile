# Stage 1: Build the application
FROM gradle:7-jdk11 AS build

# Copy the source code and change ownership to the gradle user
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory inside the container
WORKDIR /home/gradle/src

# Build the fat JAR file
RUN gradle buildFatJar --no-daemon

# Stage 2: Create the final Docker image
FROM openjdk:11

# Expose the port your application runs on
EXPOSE 8080

# Create a directory for the application
RUN mkdir /app

# Copy the compiled JAR file from the build stage to the app directory
COPY --from=build /home/gradle/src/build/libs/*.jar /app/Shorty.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/Shorty.jar"]
