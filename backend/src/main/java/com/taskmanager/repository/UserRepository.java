package com.taskmanager.repository;

import com.taskmanager.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Usuarios
 * Gestión de datos para entidades User
 * 
 * @author Andre
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Buscar usuario por nombre de usuario
     * 
     * @param username nombre de usuario
     * @return Optional<User> usuario encontrado o vacío
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar usuario por email
     * 
     * @param email email del usuario
     * @return Optional<User> usuario encontrado o vacío
     */
    Optional<User> findByEmail(String email);

    /**
     * Buscar usuario por username o email
     * 
     * @param username nombre de usuario
     * @param email email del usuario
     * @return Optional<User> usuario encontrado o vacío
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Verificar si existe un usuario con el username dado
     * 
     * @param username nombre de usuario
     * @return boolean true si existe, false si no
     */
    boolean existsByUsername(String username);

    /**
     * Verificar si existe un usuario con el email dado
     * 
     * @param email email del usuario
     * @return boolean true si existe, false si no
     */
    boolean existsByEmail(String email);

    /**
     * Buscar usuarios por rol
     * 
     * @param role rol del usuario
     * @return List<User> lista de usuarios con ese rol
     */
    List<User> findByRole(User.Role role);

    /**
     * Buscar usuarios habilitados
     * 
     * @param enabled estado de habilitación
     * @return List<User> lista de usuarios habilitados/deshabilitados
     */
    List<User> findByEnabled(Boolean enabled);

    /**
     * Buscar usuarios por nombre o apellido (búsqueda parcial)
     * 
     * @param firstName nombre
     * @param lastName apellido
     * @return List<User> lista de usuarios que coinciden
     */
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName);

    /**
     * Contar usuarios por rol
     * 
     * @param role rol del usuario
     * @return long cantidad de usuarios con ese rol
     */
    long countByRole(User.Role role);

    /**
     * Contar usuarios habilitados
     * 
     * @return long cantidad de usuarios habilitados
     */
    long countByEnabledTrue();

    /**
     * Buscar usuarios con tareas (que tienen al menos una tarea)
     * 
     * @return List<User> usuarios que tienen tareas
     */
    @Query("SELECT DISTINCT u FROM User u WHERE SIZE(u.tasks) > 0")
    List<User> findUsersWithTasks();

    /**
     * Buscar usuarios sin tareas
     * 
     * @return List<User> usuarios que no tienen tareas
     */
    @Query("SELECT u FROM User u WHERE SIZE(u.tasks) = 0")
    List<User> findUsersWithoutTasks();

    /**
     * Buscar usuarios por nombre completo
     * 
     * @param searchTerm término de búsqueda
     * @return List<User> usuarios que coinciden con la búsqueda
     */
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);

    /**
     * Obtener estadísticas de usuarios
     * 
     * @return List<Object[]> estadísticas [rol, cantidad]
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> getUserStatistics();
}
