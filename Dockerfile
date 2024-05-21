# Use Gradle image for building the application
FROM gradle:7-jdk17 AS build

# Copy the project files to the build environment and set ownership
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory
WORKDIR /home/gradle/src

# Build the fat JAR file
RUN gradle buildFatJar --no-daemon

# Use OpenJDK image for running the application
FROM openjdk:11

# Expose the application port
EXPOSE 8088

# Create an application directory
RUN mkdir /app

# Copy the built JAR file from the build stage to the application directory
COPY --from=build /home/gradle/src/build/libs/*.jar /app/Shorty.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/Shorty.jar"]
