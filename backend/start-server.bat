@echo off
echo =========================================
echo   INICIANDO TASK MANAGER BACKEND
echo =========================================
echo.

echo 1. Compilando proyecto...
call mvn compile
if %ERRORLEVEL% neq 0 (
    echo ❌ Error de compilacion
    pause
    exit /b 1
)
echo ✅ Compilacion exitosa
echo.

echo 2. Creando JAR ejecutable...
call mvn package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo ❌ Error creando JAR
    pause
    exit /b 1
)
echo ✅ JAR creado exitosamente
echo.

echo 3. Iniciando servidor Spring Boot...
echo.
echo ========================================
echo   SERVIDOR INICIADO CORRECTAMENTE
echo ========================================
echo   Backend disponible en: http://localhost:8080
echo   H2 Console disponible en: http://localhost:8080/h2-console
echo   
echo   APIs REST disponibles:
echo   • GET  http://localhost:8080/api/health
echo   • GET  http://localhost:8080/api/users
echo   • POST http://localhost:8080/api/users
echo   • GET  http://localhost:8080/api/tasks
echo   • POST http://localhost:8080/api/tasks
echo.
echo   Presiona Ctrl+C para detener el servidor
echo ========================================
echo.

java -jar target/task-manager-backend-1.0.0.jar
