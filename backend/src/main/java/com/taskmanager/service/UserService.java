package com.taskmanager.service;

import com.taskmanager.entity.User;
import com.taskmanager.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de Usuarios
 * Lógica de negocio para gestión de usuarios
 * 
 * @author Andre
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crear un nuevo usuario
     * 
     * @param user datos del usuario
     * @return User usuario creado
     * @throws IllegalArgumentException si el usuario ya existe
     */
    public User createUser(User user) {
        // Verificar si el username ya existe
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe: " + user.getUsername());
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + user.getEmail());
        }

        // Encriptar password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Asignar valores por defecto
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Buscar usuario por ID
     * 
     * @param id ID del usuario
     * @return Optional<User> usuario encontrado
     */
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Buscar usuario por username
     * 
     * @param username nombre de usuario
     * @return Optional<User> usuario encontrado
     */
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Buscar usuario por email
     * 
     * @param email email del usuario
     * @return Optional<User> usuario encontrado
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Buscar usuario por username o email
     * 
     * @param usernameOrEmail username o email
     * @return Optional<User> usuario encontrado
     */
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    /**
     * Obtener todos los usuarios
     * 
     * @return List<User> lista de todos los usuarios
     */
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Buscar usuarios por rol
     * 
     * @param role rol del usuario
     * @return List<User> usuarios con ese rol
     */
    @Transactional(readOnly = true)
    public List<User> findByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    /**
     * Buscar usuarios activos
     * 
     * @return List<User> usuarios habilitados
     */
    @Transactional(readOnly = true)
    public List<User> findActiveUsers() {
        return userRepository.findByEnabled(true);
    }

    /**
     * Actualizar usuario
     * 
     * @param id ID del usuario
     * @param userDetails nuevos datos del usuario
     * @return User usuario actualizado
     * @throws RuntimeException si el usuario no existe
     */
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Verificar username único (si cambió)
        if (!user.getUsername().equals(userDetails.getUsername()) && 
            userRepository.existsByUsername(userDetails.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe: " + userDetails.getUsername());
        }

        // Verificar email único (si cambió)
        if (!user.getEmail().equals(userDetails.getEmail()) && 
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + userDetails.getEmail());
        }

        // Actualizar campos
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setRole(userDetails.getRole());
        user.setEnabled(userDetails.getEnabled());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Cambiar contraseña de usuario
     * 
     * @param id ID del usuario
     * @param newPassword nueva contraseña
     * @return User usuario actualizado
     */
    public User changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Habilitar/deshabilitar usuario
     * 
     * @param id ID del usuario
     * @param enabled estado habilitado
     * @return User usuario actualizado
     */
    public User toggleUserStatus(Long id, boolean enabled) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        user.setEnabled(enabled);
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Eliminar usuario
     * 
     * @param id ID del usuario
     * @throws RuntimeException si el usuario no existe
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Verificar si un usuario existe por username
     * 
     * @param username nombre de usuario
     * @return boolean true si existe
     */
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Verificar si un usuario existe por email
     * 
     * @param email email del usuario
     * @return boolean true si existe
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Buscar usuarios con tareas
     * 
     * @return List<User> usuarios que tienen tareas asignadas
     */
    @Transactional(readOnly = true)
    public List<User> findUsersWithTasks() {
        return userRepository.findUsersWithTasks();
    }

    /**
     * Buscar usuarios sin tareas
     * 
     * @return List<User> usuarios sin tareas asignadas
     */
    @Transactional(readOnly = true)
    public List<User> findUsersWithoutTasks() {
        return userRepository.findUsersWithoutTasks();
    }

    /**
     * Búsqueda de usuarios por texto
     * 
     * @param searchTerm término de búsqueda
     * @return List<User> usuarios que coinciden con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<User> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm);
    }

    /**
     * Contar usuarios por rol
     * 
     * @param role rol del usuario
     * @return long cantidad de usuarios con ese rol
     */
    @Transactional(readOnly = true)
    public long countByRole(User.Role role) {
        return userRepository.countByRole(role);
    }

    /**
     * Obtener estadísticas de usuarios
     * 
     * @return List<Object[]> estadísticas [rol, cantidad]
     */
    @Transactional(readOnly = true)
    public List<Object[]> getUserStatistics() {
        return userRepository.getUserStatistics();
    }

    /**
     * Validar credenciales de usuario
     * 
     * @param usernameOrEmail username o email
     * @param password contraseña
     * @return boolean true si las credenciales son válidas
     */
    @Transactional(readOnly = true)
    public boolean validateCredentials(String usernameOrEmail, String password) {
        Optional<User> userOpt = findByUsernameOrEmail(usernameOrEmail);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getEnabled() && passwordEncoder.matches(password, user.getPassword());
        }
        
        return false;
    }
}
