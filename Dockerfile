FROM gradle:7-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/PaviconTechComm.jar
# Copy .env file
COPY .env /app/.env
ENTRYPOINT ["java","-jar","/app/PaviconTechComm.jar"]