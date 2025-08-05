package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Tareas
 * Gestión de datos para entidades Task
 * 
 * @author Andre
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Buscar tareas por usuario
     * 
     * @param user usuario propietario
     * @return List<Task> lista de tareas del usuario
     */
    List<Task> findByUser(User user);

    /**
     * Buscar tareas por ID de usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> lista de tareas del usuario
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId")
    List<Task> findByUserId(@Param("userId") Long userId);

    /**
     * Buscar tareas por estado
     * 
     * @param status estado de la tarea
     * @return List<Task> lista de tareas con ese estado
     */
    List<Task> findByStatus(Task.Status status);

    /**
     * Buscar tareas por prioridad
     * 
     * @param priority prioridad de la tarea
     * @return List<Task> lista de tareas con esa prioridad
     */
    List<Task> findByPriority(Task.Priority priority);

    /**
     * Buscar tareas por usuario y estado
     * 
     * @param user usuario
     * @param status estado
     * @return List<Task> lista de tareas filtradas
     */
    List<Task> findByUserAndStatus(User user, Task.Status status);

    /**
     * Buscar tareas por usuario y prioridad
     * 
     * @param user usuario
     * @param priority prioridad
     * @return List<Task> lista de tareas filtradas
     */
    List<Task> findByUserAndPriority(User user, Task.Priority priority);

    /**
     * Buscar tareas completadas por usuario
     * 
     * @param userId ID del usuario
     * @return List<Task> tareas completadas
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.status = :status")
    List<Task> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Task.Status status);

    /**
     * Buscar tareas vencidas (fecha límite pasada y no completadas)
     * 
     * @param currentDate fecha actual
     * @return List<Task> tareas vencidas
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate < :currentDate AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks(@Param("currentDate") LocalDateTime currentDate);

    /**
     * Buscar tareas vencidas por usuario
     * 
     * @param userId ID del usuario
     * @param currentDate fecha actual
     * @return List<Task> tareas vencidas del usuario
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.dueDate < :currentDate AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasksByUser(@Param("userId") Long userId, @Param("currentDate") LocalDateTime currentDate);

    /**
     * Buscar tareas por rango de fechas de creación
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return List<Task> tareas creadas en el rango
     */
    List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Buscar tareas por rango de fechas de vencimiento
     * 
     * @param startDate fecha de inicio
     * @param endDate fecha de fin
     * @return List<Task> tareas con vencimiento en el rango
     */
    List<Task> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Buscar tareas por título (búsqueda parcial)
     * 
     * @param title parte del título
     * @return List<Task> tareas que contienen el texto en el título
     */
    List<Task> findByTitleContainingIgnoreCase(String title);

    /**
     * Buscar tareas por descripción (búsqueda parcial)
     * 
     * @param description parte de la descripción
     * @return List<Task> tareas que contienen el texto en la descripción
     */
    List<Task> findByDescriptionContainingIgnoreCase(String description);

    /**
     * Buscar tareas por usuario ordenadas por fecha de creación
     * 
     * @param user usuario
     * @return List<Task> tareas ordenadas por fecha de creación descendente
     */
    List<Task> findByUserOrderByCreatedAtDesc(User user);

    /**
     * Buscar tareas por usuario ordenadas por fecha de vencimiento
     * 
     * @param user usuario
     * @return List<Task> tareas ordenadas por fecha de vencimiento ascendente
     */
    List<Task> findByUserOrderByDueDateAsc(User user);

    /**
     * Buscar tareas por usuario ordenadas por prioridad
     * 
     * @param user usuario
     * @return List<Task> tareas ordenadas por prioridad descendente
     */
    List<Task> findByUserOrderByPriorityDesc(User user);

    /**
     * Contar tareas por estado
     * 
     * @param status estado
     * @return long cantidad de tareas con ese estado
     */
    long countByStatus(Task.Status status);

    /**
     * Contar tareas por usuario
     * 
     * @param user usuario
     * @return long cantidad de tareas del usuario
     */
    long countByUser(User user);

    /**
     * Contar tareas completadas por usuario
     * 
     * @param userId ID del usuario
     * @return long cantidad de tareas completadas
     */
    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId AND t.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Task.Status status);

    /**
     * Buscar tareas por múltiples criterios
     * 
     * @param searchTerm término de búsqueda
     * @param userId ID del usuario
     * @return List<Task> tareas que coinciden con la búsqueda
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Task> searchTasksByUser(@Param("searchTerm") String searchTerm, @Param("userId") Long userId);

    /**
     * Obtener estadísticas de tareas por estado
     * 
     * @return List<Object[]> estadísticas [estado, cantidad]
     */
    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> getTaskStatisticsByStatus();

    /**
     * Obtener estadísticas de tareas por prioridad
     * 
     * @return List<Object[]> estadísticas [prioridad, cantidad]
     */
    @Query("SELECT t.priority, COUNT(t) FROM Task t GROUP BY t.priority")
    List<Object[]> getTaskStatisticsByPriority();

    /**
     * Obtener tareas próximas a vencer (en los próximos días)
     * 
     * @param userId ID del usuario
     * @param days número de días hacia adelante
     * @return List<Task> tareas próximas a vencer
     */
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND " +
           "t.dueDate BETWEEN CURRENT_TIMESTAMP AND :futureDate AND " +
           "t.status != 'COMPLETED'")
    List<Task> findTasksDueSoon(@Param("userId") Long userId, @Param("futureDate") LocalDateTime futureDate);
}
