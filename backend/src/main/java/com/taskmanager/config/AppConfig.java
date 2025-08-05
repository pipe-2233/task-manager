package com.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de beans para la aplicación
 * 
 * @author Task Manager Team
 * @version 1.0.0
 */
@Configuration
public class AppConfig {

    /**
     * Bean para codificación de contraseñas
     * 
     * @return PasswordEncoder codificador BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
