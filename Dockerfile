# Etapa 1: Construir el JAR
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src ./src
# Dar permisos de ejecución al wrapper (¡esto soluciona tu error!)
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen ligera para ejecutar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/backend-ia-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]