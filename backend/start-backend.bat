@echo off
echo ========================================
echo  INICIANDO TASK MANAGER BACKEND
echo ========================================
echo.

cd /d "c:\Users\andre\OneDrive\Desktop\empleo\task-manager-v2\backend"

echo Compilando proyecto...
call mvn clean compile

echo.
echo Iniciando servidor Spring Boot...
echo Backend disponible en: http://localhost:8080
echo H2 Console disponible en: http://localhost:8080/h2-console
echo.

call mvn org.springframework.boot:spring-boot-maven-plugin:3.2.2:run

pause
