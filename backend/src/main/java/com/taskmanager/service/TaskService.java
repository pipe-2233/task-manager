package com.taskmanager.service;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de Tareas
 * Lógica de negocio para gestión de tareas
 * 
 * @author Andre
 */
@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Crear una nueva tarea
     * 
     * @param task datos de la tarea
     * @param userId ID del usuario propietario
     * @return Task tarea creada
     * @throws RuntimeException si el usuario no existe
     */
    public Task createTask(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        task.setUser(user);
        
        // Asignar valores por defecto
        if (task.getStatus() == null) {
            task.setStatus(Task.Status.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(Task.Priority.MEDIUM);
        }
        
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Buscar tarea por ID
     * 
     * @param id ID de la tarea
     * @return Optional<Task> tarea encontrada
     */
    @Transactional(readOnly = true)
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Obtener todas las tareas
     * 
     * @return List<Task> lista de todas las tareas
     */
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    /**
     * Buscar tareas por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas del usuario
     */
    @Transactional(readOnly = true)
    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    /**
     * Buscar tareas por usuario (entidad)
     * 
     * @param user usuario
     * @return List<Task> tareas del usuario
     */
    @Transactional(readOnly = true)
    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    /**
     * Buscar tareas por estado
     * 
     * @param status estado de la tarea
     * @return List<Task> tareas con ese estado
     */
    @Transactional(readOnly = true)
    public List<Task> findByStatus(Task.Status status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Buscar tareas por prioridad
     * 
     * @param priority prioridad de la tarea
     * @return List<Task> tareas con esa prioridad
     */
    @Transactional(readOnly = true)
    public List<Task> findByPriority(Task.Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    /**
     * Buscar tareas por usuario y estado
     * 
     * @param userId ID del usuario
     * @param status estado de la tarea
     * @return List<Task> tareas filtradas
     */
    @Transactional(readOnly = true)
    public List<Task> findByUserIdAndStatus(Long userId, Task.Status status) {
        return taskRepository.findByUserIdAndStatus(userId, status);
    }

    /**
     * Buscar tareas completadas por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas completadas
     */
    @Transactional(readOnly = true)
    public List<Task> findCompletedTasksByUser(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Task.Status.COMPLETED);
    }

    /**
     * Buscar tareas pendientes por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas pendientes
     */
    @Transactional(readOnly = true)
    public List<Task> findPendingTasksByUser(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Task.Status.PENDING);
    }

    /**
     * Buscar tareas en progreso por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas en progreso
     */
    @Transactional(readOnly = true)
    public List<Task> findInProgressTasksByUser(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Task.Status.IN_PROGRESS);
    }

    /**
     * Buscar tareas vencidas
     * 
     * @return List<Task> tareas vencidas
     */
    @Transactional(readOnly = true)
    public List<Task> findOverdueTasks() {
        return taskRepository.findOverdueTasks(LocalDateTime.now());
    }

    /**
     * Buscar tareas vencidas por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas vencidas del usuario
     */
    @Transactional(readOnly = true)
    public List<Task> findOverdueTasksByUser(Long userId) {
        return taskRepository.findOverdueTasksByUser(userId, LocalDateTime.now());
    }

    /**
     * Buscar tareas próximas a vencer
     * 
     * @param userId ID del usuario
     * @param days número de días hacia adelante
     * @return List<Task> tareas próximas a vencer
     */
    @Transactional(readOnly = true)
    public List<Task> findTasksDueSoon(Long userId, int days) {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(days);
        return taskRepository.findTasksDueSoon(userId, futureDate);
    }

    /**
     * Buscar tareas por rango de fechas de creación
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return List<Task> tareas creadas en el rango
     */
    @Transactional(readOnly = true)
    public List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByCreatedAtBetween(startDate, endDate);
    }

    /**
     * Buscar tareas por rango de fechas de vencimiento
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return List<Task> tareas con vencimiento en el rango
     */
    @Transactional(readOnly = true)
    public List<Task> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    /**
     * Buscar tareas por título (búsqueda parcial)
     * 
     * @param title parte del título
     * @return List<Task> tareas que contienen el texto en el título
     */
    @Transactional(readOnly = true)
    public List<Task> findByTitleContaining(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Buscar tareas por descripción (búsqueda parcial)
     * 
     * @param description parte de la descripción
     * @return List<Task> tareas que contienen el texto en la descripción
     */
    @Transactional(readOnly = true)
    public List<Task> findByDescriptionContaining(String description) {
        return taskRepository.findByDescriptionContainingIgnoreCase(description);
    }

    /**
     * Buscar tareas por usuario ordenadas por fecha de creación
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas ordenadas por fecha de creación descendente
     */
    @Transactional(readOnly = true)
    public List<Task> findByUserOrderByCreatedAt(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        return taskRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /**
     * Buscar tareas por usuario ordenadas por fecha de vencimiento
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas ordenadas por fecha de vencimiento ascendente
     */
    @Transactional(readOnly = true)
    public List<Task> findByUserOrderByDueDate(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        return taskRepository.findByUserOrderByDueDateAsc(user);
    }

    /**
     * Buscar tareas por usuario ordenadas por prioridad
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas ordenadas por prioridad descendente
     */
    @Transactional(readOnly = true)
    public List<Task> findByUserOrderByPriority(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        return taskRepository.findByUserOrderByPriorityDesc(user);
    }

    /**
     * Búsqueda de tareas por usuario
     * 
     * @param searchTerm término de búsqueda
     * @param userId ID del usuario
     * @return List<Task> tareas que coinciden con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<Task> searchTasksByUser(String searchTerm, Long userId) {
        return taskRepository.searchTasksByUser(searchTerm, userId);
    }

    /**
     * Actualizar tarea
     * 
     * @param id ID de la tarea
     * @param taskDetails nuevos datos de la tarea
     * @return Task tarea actualizada
     * @throws RuntimeException si la tarea no existe
     */
    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        // Actualizar campos
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Cambiar estado de tarea
     * 
     * @param id ID de la tarea
     * @param status nuevo estado
     * @return Task tarea actualizada
     */
    public Task changeTaskStatus(Long id, Task.Status status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Marcar tarea como completada
     * 
     * @param id ID de la tarea
     * @return Task tarea completada
     */
    public Task completeTask(Long id) {
        return changeTaskStatus(id, Task.Status.COMPLETED);
    }

    /**
     * Cambiar prioridad de tarea
     * 
     * @param id ID de la tarea
     * @param priority nueva prioridad
     * @return Task tarea actualizada
     */
    public Task changeTaskPriority(Long id, Task.Priority priority) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        task.setPriority(priority);
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Eliminar tarea
     * 
     * @param id ID de la tarea
     * @throws RuntimeException si la tarea no existe
     */
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    /**
     * Contar tareas por estado
     * 
     * @param status estado
     * @return long cantidad de tareas con ese estado
     */
    @Transactional(readOnly = true)
    public long countByStatus(Task.Status status) {
        return taskRepository.countByStatus(status);
    }

    /**
     * Contar tareas por usuario
     * 
     * @param userId ID del usuario
     * @return long cantidad de tareas del usuario
     */
    @Transactional(readOnly = true)
    public long countByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));
        return taskRepository.countByUser(user);
    }

    /**
     * Contar tareas completadas por usuario
     * 
     * @param userId ID del usuario
     * @return long cantidad de tareas completadas
     */
    @Transactional(readOnly = true)
    public long countCompletedTasksByUser(Long userId) {
        return taskRepository.countByUserIdAndStatus(userId, Task.Status.COMPLETED);
    }

    /**
     * Obtener estadísticas de tareas por estado
     * 
     * @return List<Object[]> estadísticas [estado, cantidad]
     */
    @Transactional(readOnly = true)
    public List<Object[]> getTaskStatisticsByStatus() {
        return taskRepository.getTaskStatisticsByStatus();
    }

    /**
     * Obtener estadísticas de tareas por prioridad
     * 
     * @return List<Object[]> estadísticas [prioridad, cantidad]
     */
    @Transactional(readOnly = true)
    public List<Object[]> getTaskStatisticsByPriority() {
        return taskRepository.getTaskStatisticsByPriority();
    }

    /**
     * Verificar si una tarea pertenece a un usuario
     * 
     * @param taskId ID de la tarea
     * @param userId ID del usuario
     * @return boolean true si la tarea pertenece al usuario
     */
    @Transactional(readOnly = true)
    public boolean isTaskOwnedByUser(Long taskId, Long userId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            return task.getUser().getId().equals(userId);
        }
        return false;
    }

    /**
     * Obtener resumen de tareas del usuario
     * 
     * @param userId ID del usuario
     * @return TaskSummary resumen de tareas
     */
    @Transactional(readOnly = true)
    public TaskSummary getUserTaskSummary(Long userId) {
        long totalTasks = countByUserId(userId);
        long completedTasks = countCompletedTasksByUser(userId);
        long pendingTasks = taskRepository.countByUserIdAndStatus(userId, Task.Status.PENDING);
        long inProgressTasks = taskRepository.countByUserIdAndStatus(userId, Task.Status.IN_PROGRESS);
        long overdueTasks = findOverdueTasksByUser(userId).size();

        return new TaskSummary(totalTasks, completedTasks, pendingTasks, inProgressTasks, overdueTasks);
    }

    /**
     * Clase interna para resumen de tareas
     */
    public static class TaskSummary {
        private final long totalTasks;
        private final long completedTasks;
        private final long pendingTasks;
        private final long inProgressTasks;
        private final long overdueTasks;

        public TaskSummary(long totalTasks, long completedTasks, long pendingTasks, long inProgressTasks, long overdueTasks) {
            this.totalTasks = totalTasks;
            this.completedTasks = completedTasks;
            this.pendingTasks = pendingTasks;
            this.inProgressTasks = inProgressTasks;
            this.overdueTasks = overdueTasks;
        }

        // Getters
        public long getTotalTasks() { return totalTasks; }
        public long getCompletedTasks() { return completedTasks; }
        public long getPendingTasks() { return pendingTasks; }
        public long getInProgressTasks() { return inProgressTasks; }
        public long getOverdueTasks() { return overdueTasks; }
    }
}
