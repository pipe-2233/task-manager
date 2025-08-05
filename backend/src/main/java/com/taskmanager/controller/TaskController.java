package com.taskmanager.controller;

import com.taskmanager.entity.Task;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.TaskService.TaskSummary;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API REST - Tareas
 * Endpoints para gestión de tareas
 * 
 * @author Andre
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Obtener todas las tareas
     * GET /api/tasks
     * 
     * @return ResponseEntity<List<Task>> lista de tareas
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = taskService.findAll();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tarea por ID
     * GET /api/tasks/{id}
     * 
     * @param id ID de la tarea
     * @return ResponseEntity<Task> tarea encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            Optional<Task> task = taskService.findById(id);
            return task.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por usuario
     * GET /api/tasks/user/{userId}
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<List<Task>> tareas del usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.findByUserId(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por estado
     * GET /api/tasks/status/{status}
     * 
     * @param status estado de la tarea
     * @return ResponseEntity<List<Task>> tareas con ese estado
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Task.Status status) {
        try {
            List<Task> tasks = taskService.findByStatus(status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por prioridad
     * GET /api/tasks/priority/{priority}
     * 
     * @param priority prioridad de la tarea
     * @return ResponseEntity<List<Task>> tareas con esa prioridad
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Task.Priority priority) {
        try {
            List<Task> tasks = taskService.findByPriority(priority);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por usuario y estado
     * GET /api/tasks/user/{userId}/status/{status}
     * 
     * @param userId ID del usuario
     * @param status estado de la tarea
     * @return ResponseEntity<List<Task>> tareas filtradas
     */
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Task>> getTasksByUserAndStatus(@PathVariable Long userId, @PathVariable Task.Status status) {
        try {
            List<Task> tasks = taskService.findByUserIdAndStatus(userId, status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas completadas por usuario
     * GET /api/tasks/user/{userId}/completed
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<List<Task>> tareas completadas
     */
    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<Task>> getCompletedTasksByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.findCompletedTasksByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas pendientes por usuario
     * GET /api/tasks/user/{userId}/pending
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<List<Task>> tareas pendientes
     */
    @GetMapping("/user/{userId}/pending")
    public ResponseEntity<List<Task>> getPendingTasksByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.findPendingTasksByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas en progreso por usuario
     * GET /api/tasks/user/{userId}/in-progress
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<List<Task>> tareas en progreso
     */
    @GetMapping("/user/{userId}/in-progress")
    public ResponseEntity<List<Task>> getInProgressTasksByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.findInProgressTasksByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas vencidas
     * GET /api/tasks/overdue
     * 
     * @return ResponseEntity<List<Task>> tareas vencidas
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        try {
            List<Task> tasks = taskService.findOverdueTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas vencidas por usuario
     * GET /api/tasks/user/{userId}/overdue
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<List<Task>> tareas vencidas del usuario
     */
    @GetMapping("/user/{userId}/overdue")
    public ResponseEntity<List<Task>> getOverdueTasksByUser(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.findOverdueTasksByUser(userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas próximas a vencer
     * GET /api/tasks/user/{userId}/due-soon?days={days}
     * 
     * @param userId ID del usuario
     * @param days número de días hacia adelante (default: 7)
     * @return ResponseEntity<List<Task>> tareas próximas a vencer
     */
    @GetMapping("/user/{userId}/due-soon")
    public ResponseEntity<List<Task>> getTasksDueSoon(@PathVariable Long userId, @RequestParam(defaultValue = "7") int days) {
        try {
            List<Task> tasks = taskService.findTasksDueSoon(userId, days);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar tareas por título
     * GET /api/tasks/search/title?q={searchTerm}
     * 
     * @param searchTerm término de búsqueda
     * @return ResponseEntity<List<Task>> tareas que coinciden
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<Task>> searchTasksByTitle(@RequestParam String q) {
        try {
            List<Task> tasks = taskService.findByTitleContaining(q);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar tareas por descripción
     * GET /api/tasks/search/description?q={searchTerm}
     * 
     * @param searchTerm término de búsqueda
     * @return ResponseEntity<List<Task>> tareas que coinciden
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<Task>> searchTasksByDescription(@RequestParam String q) {
        try {
            List<Task> tasks = taskService.findByDescriptionContaining(q);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar tareas por usuario
     * GET /api/tasks/user/{userId}/search?q={searchTerm}
     * 
     * @param userId ID del usuario
     * @param searchTerm término de búsqueda
     * @return ResponseEntity<List<Task>> tareas que coinciden
     */
    @GetMapping("/user/{userId}/search")
    public ResponseEntity<List<Task>> searchTasksByUser(@PathVariable Long userId, @RequestParam String q) {
        try {
            List<Task> tasks = taskService.searchTasksByUser(q, userId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por rango de fechas de creación
     * GET /api/tasks/created-between?start={startDate}&end={endDate}
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return ResponseEntity<List<Task>> tareas creadas en el rango
     */
    @GetMapping("/created-between")
    public ResponseEntity<List<Task>> getTasksByCreatedDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<Task> tasks = taskService.findByCreatedAtBetween(start, end);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener tareas por rango de fechas de vencimiento
     * GET /api/tasks/due-between?start={startDate}&end={endDate}
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return ResponseEntity<List<Task>> tareas con vencimiento en el rango
     */
    @GetMapping("/due-between")
    public ResponseEntity<List<Task>> getTasksByDueDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<Task> tasks = taskService.findByDueDateBetween(start, end);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nueva tarea
     * POST /api/tasks
     * 
     * @param taskRequest datos de la tarea
     * @return ResponseEntity<Task> tarea creada
     */
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskCreateRequest taskRequest) {
        try {
            Task task = new Task();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setStatus(taskRequest.getStatus());
            task.setPriority(taskRequest.getPriority());
            task.setDueDate(taskRequest.getDueDate());

            Task createdTask = taskService.createTask(task, taskRequest.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error de validación", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al crear tarea"));
        }
    }

    /**
     * Actualizar tarea
     * PUT /api/tasks/{id}
     * 
     * @param id ID de la tarea
     * @param taskDetails nuevos datos de la tarea
     * @return ResponseEntity<Task> tarea actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al actualizar tarea"));
        }
    }

    /**
     * Cambiar estado de tarea
     * PUT /api/tasks/{id}/status
     * 
     * @param id ID de la tarea
     * @param statusRequest nuevo estado
     * @return ResponseEntity<Task> tarea actualizada
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long id, @RequestBody TaskStatusRequest statusRequest) {
        try {
            Task updatedTask = taskService.changeTaskStatus(id, statusRequest.getStatus());
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al cambiar estado de tarea"));
        }
    }

    /**
     * Marcar tarea como completada
     * PUT /api/tasks/{id}/complete
     * 
     * @param id ID de la tarea
     * @return ResponseEntity<Task> tarea completada
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long id) {
        try {
            Task completedTask = taskService.completeTask(id);
            return ResponseEntity.ok(completedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al completar tarea"));
        }
    }

    /**
     * Cambiar prioridad de tarea
     * PUT /api/tasks/{id}/priority
     * 
     * @param id ID de la tarea
     * @param priorityRequest nueva prioridad
     * @return ResponseEntity<Task> tarea actualizada
     */
    @PutMapping("/{id}/priority")
    public ResponseEntity<?> changeTaskPriority(@PathVariable Long id, @RequestBody TaskPriorityRequest priorityRequest) {
        try {
            Task updatedTask = taskService.changeTaskPriority(id, priorityRequest.getPriority());
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al cambiar prioridad de tarea"));
        }
    }

    /**
     * Eliminar tarea
     * DELETE /api/tasks/{id}
     * 
     * @param id ID de la tarea
     * @return ResponseEntity<Void> respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al eliminar tarea"));
        }
    }

    /**
     * Verificar si tarea pertenece a usuario
     * GET /api/tasks/{taskId}/belongs-to/{userId}
     * 
     * @param taskId ID de la tarea
     * @param userId ID del usuario
     * @return ResponseEntity<Boolean> true si pertenece
     */
    @GetMapping("/{taskId}/belongs-to/{userId}")
    public ResponseEntity<Boolean> isTaskOwnedByUser(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            boolean isOwned = taskService.isTaskOwnedByUser(taskId, userId);
            return ResponseEntity.ok(isOwned);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener resumen de tareas del usuario
     * GET /api/tasks/user/{userId}/summary
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<TaskSummary> resumen de tareas
     */
    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<TaskSummary> getUserTaskSummary(@PathVariable Long userId) {
        try {
            TaskSummary summary = taskService.getUserTaskSummary(userId);
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener estadísticas de tareas por estado
     * GET /api/tasks/statistics/status
     * 
     * @return ResponseEntity<List<Object[]>> estadísticas
     */
    @GetMapping("/statistics/status")
    public ResponseEntity<List<Object[]>> getTaskStatisticsByStatus() {
        try {
            List<Object[]> stats = taskService.getTaskStatisticsByStatus();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener estadísticas de tareas por prioridad
     * GET /api/tasks/statistics/priority
     * 
     * @return ResponseEntity<List<Object[]>> estadísticas
     */
    @GetMapping("/statistics/priority")
    public ResponseEntity<List<Object[]>> getTaskStatisticsByPriority() {
        try {
            List<Object[]> stats = taskService.getTaskStatisticsByPriority();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Contar tareas por estado
     * GET /api/tasks/count/status/{status}
     * 
     * @param status estado de la tarea
     * @return ResponseEntity<Long> cantidad
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countByStatus(@PathVariable Task.Status status) {
        try {
            long count = taskService.countByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Contar tareas por usuario
     * GET /api/tasks/count/user/{userId}
     * 
     * @param userId ID del usuario
     * @return ResponseEntity<Long> cantidad
     */
    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long> countByUser(@PathVariable Long userId) {
        try {
            long count = taskService.countByUserId(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Clases internas para requests

    /**
     * Request para crear tarea
     */
    public static class TaskCreateRequest {
        private String title;
        private String description;
        private Task.Status status;
        private Task.Priority priority;
        private LocalDateTime dueDate;
        private Long userId;

        // Getters y Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Task.Status getStatus() { return status; }
        public void setStatus(Task.Status status) { this.status = status; }
        
        public Task.Priority getPriority() { return priority; }
        public void setPriority(Task.Priority priority) { this.priority = priority; }
        
        public LocalDateTime getDueDate() { return dueDate; }
        public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    /**
     * Request para cambio de estado
     */
    public static class TaskStatusRequest {
        private Task.Status status;

        public Task.Status getStatus() { return status; }
        public void setStatus(Task.Status status) { this.status = status; }
    }

    /**
     * Request para cambio de prioridad
     */
    public static class TaskPriorityRequest {
        private Task.Priority priority;

        public Task.Priority getPriority() { return priority; }
        public void setPriority(Task.Priority priority) { this.priority = priority; }
    }

    /**
     * Clase para respuestas de error
     */
    public static class ErrorResponse {
        private final String error;
        private final String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public String getMessage() { return message; }
    }
}
