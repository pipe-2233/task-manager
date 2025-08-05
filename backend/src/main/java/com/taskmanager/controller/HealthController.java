package com.taskmanager.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de prueba para verificar que la aplicación funciona
 * 
 * @author Task Manager Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Endpoint de salud básico
     * 
     * @return Información del estado de la aplicación
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Task Manager Backend is running!");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de bienvenida
     * 
     * @return Mensaje de bienvenida
     */
    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "¡Bienvenido al Task Manager API!");
        response.put("description", "Sistema de gestión de tareas desarrollado con Spring Boot 3.2.2");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint público de información
     * 
     * @return Información básica de la API
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Task Manager Backend");
        response.put("version", "1.0.0");
        response.put("framework", "Spring Boot 3.2.2");
        response.put("java", "Java 17");
        response.put("database", "H2 (Development)");
        
        return ResponseEntity.ok(response);
    }
}
