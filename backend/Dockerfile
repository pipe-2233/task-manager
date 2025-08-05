# Dockerfile para Spring Boot Task Manager
FROM maven:3.8.6-openjdk-17-slim AS build

# Directorio de trabajo
WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y construir
COPY src/ src/
RUN mvn clean package -DskipTests

# Imagen final con solo JRE
FROM openjdk:17-jre-slim

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR construido
COPY --from=build /app/target/task-manager-backend-1.0.0.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
