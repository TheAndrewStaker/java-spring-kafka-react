# ---- Build stage
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle /app/gradle
RUN ./gradlew --version

COPY src /app/src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
