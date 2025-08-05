package com.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de bienvenida
 * 
 * @author Andre
 */
@RestController
public class WelcomeController {

    @GetMapping("/")
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🎯 Task Manager API está funcionando!");
        response.put("version", "1.0.0");
        response.put("endpoints", new String[]{
            "/api/users - Gestión de usuarios",
            "/api/tasks - Gestión de tareas", 
            "/h2-console - Base de datos (desarrollo)"
        });
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "Task Manager Backend");
        return status;
    }
}
