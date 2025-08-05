# Dockerfile para Spring Boot Task Manager
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml y descargar dependencias
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src/ src/

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "target/task-manager-backend-1.0.0.jar"]
