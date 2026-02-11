# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]