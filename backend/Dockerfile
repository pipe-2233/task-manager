# Dockerfile para Spring Boot Task Manager
FROM maven:3.8.6-openjdk-17 AS build

# Directorio de trabajo
WORKDIR /build

# Copiar archivos necesarios
COPY pom.xml .
COPY src src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Imagen de ejecución
FROM openjdk:17-jdk-slim

# Copiar el JAR generado
COPY --from=build /build/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
