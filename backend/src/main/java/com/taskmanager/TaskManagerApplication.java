package com.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Aplicación principal Task Manager
 * Sistema de gestión de tareas con Spring Boot
 * 
 * @author Andre
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class TaskManagerApplication {

    /**
     * Punto de entrada de la aplicación
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
