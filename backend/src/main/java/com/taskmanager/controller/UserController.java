package com.taskmanager.controller;

import com.taskmanager.entity.User;
import com.taskmanager.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API REST - Usuarios
 * Endpoints para gestión de usuarios
 * 
 * @author Andre
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtener todos los usuarios
     * GET /api/users
     * 
     * @return ResponseEntity<List<User>> lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por ID
     * GET /api/users/{id}
     * 
     * @param id ID del usuario
     * @return ResponseEntity<User> usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.findById(id);
            return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por username
     * GET /api/users/username/{username}
     * 
     * @param username nombre de usuario
     * @return ResponseEntity<User> usuario encontrado
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            Optional<User> user = userService.findByUsername(username);
            return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuario por email
     * GET /api/users/email/{email}
     * 
     * @param email email del usuario
     * @return ResponseEntity<User> usuario encontrado
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userService.findByEmail(email);
            return user.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar usuarios por rol
     * GET /api/users/role/{role}
     * 
     * @param role rol del usuario
     * @return ResponseEntity<List<User>> usuarios con ese rol
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable User.Role role) {
        try {
            List<User> users = userService.findByRole(role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener usuarios activos
     * GET /api/users/active
     * 
     * @return ResponseEntity<List<User>> usuarios habilitados
     */
    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        try {
            List<User> users = userService.findActiveUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar usuarios con tareas
     * GET /api/users/with-tasks
     * 
     * @return ResponseEntity<List<User>> usuarios que tienen tareas
     */
    @GetMapping("/with-tasks")
    public ResponseEntity<List<User>> getUsersWithTasks() {
        try {
            List<User> users = userService.findUsersWithTasks();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar usuarios sin tareas
     * GET /api/users/without-tasks
     * 
     * @return ResponseEntity<List<User>> usuarios sin tareas
     */
    @GetMapping("/without-tasks")
    public ResponseEntity<List<User>> getUsersWithoutTasks() {
        try {
            List<User> users = userService.findUsersWithoutTasks();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Buscar usuarios por texto
     * GET /api/users/search?q={searchTerm}
     * 
     * @param searchTerm término de búsqueda
     * @return ResponseEntity<List<User>> usuarios que coinciden
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String q) {
        try {
            List<User> users = userService.searchUsers(q);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear nuevo usuario
     * POST /api/users
     * 
     * @param user datos del usuario
     * @return ResponseEntity<User> usuario creado
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error de validación", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al crear usuario"));
        }
    }

    /**
     * Actualizar usuario
     * PUT /api/users/{id}
     * 
     * @param id ID del usuario
     * @param userDetails nuevos datos del usuario
     * @return ResponseEntity<User> usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error de validación", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al actualizar usuario"));
        }
    }

    /**
     * Cambiar contraseña de usuario
     * PUT /api/users/{id}/password
     * 
     * @param id ID del usuario
     * @param passwordRequest nueva contraseña
     * @return ResponseEntity<User> usuario actualizado
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordRequest) {
        try {
            User updatedUser = userService.changePassword(id, passwordRequest.getNewPassword());
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al cambiar contraseña"));
        }
    }

    /**
     * Habilitar/deshabilitar usuario
     * PUT /api/users/{id}/status
     * 
     * @param id ID del usuario
     * @param statusRequest nuevo estado
     * @return ResponseEntity<User> usuario actualizado
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id, @RequestBody UserStatusRequest statusRequest) {
        try {
            User updatedUser = userService.toggleUserStatus(id, statusRequest.isEnabled());
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al cambiar estado del usuario"));
        }
    }

    /**
     * Eliminar usuario
     * DELETE /api/users/{id}
     * 
     * @param id ID del usuario
     * @return ResponseEntity<Void> respuesta vacía
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error al eliminar usuario"));
        }
    }

    /**
     * Verificar si username existe
     * GET /api/users/exists/username/{username}
     * 
     * @param username nombre de usuario
     * @return ResponseEntity<Boolean> true si existe
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        try {
            boolean exists = userService.existsByUsername(username);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Verificar si email existe
     * GET /api/users/exists/email/{email}
     * 
     * @param email email del usuario
     * @return ResponseEntity<Boolean> true si existe
     */
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener estadísticas de usuarios
     * GET /api/users/statistics
     * 
     * @return ResponseEntity<List<Object[]>> estadísticas
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getUserStatistics() {
        try {
            List<Object[]> stats = userService.getUserStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Contar usuarios por rol
     * GET /api/users/count/role/{role}
     * 
     * @param role rol del usuario
     * @return ResponseEntity<Long> cantidad
     */
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countByRole(@PathVariable User.Role role) {
        try {
            long count = userService.countByRole(role);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Clases internas para requests
    
    /**
     * Request para cambio de contraseña
     */
    public static class PasswordChangeRequest {
        private String newPassword;

        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    /**
     * Request para cambio de estado
     */
    public static class UserStatusRequest {
        private boolean enabled;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
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
