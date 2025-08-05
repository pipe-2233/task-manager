# Dockerfile simple para Spring Boot
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Directorio de trabajo
WORKDIR /app

# Copiar archivos
COPY pom.xml .
COPY src src

# Construir aplicación
RUN mvn clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Ejecutar aplicación
CMD ["java", "-jar", "target/task-manager-backend-1.0.0.jar"]
