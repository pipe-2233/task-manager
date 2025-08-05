package com.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Seguridad
 * 
 * @author Andre
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuración de seguridad para desarrollo
     * Permite acceso sin autenticación a ciertos endpoints
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF para desarrollo y APIs REST
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configurar headers para permitir H2 Console
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
            )
            
            // Configurar autorización de requests
            .authorizeHttpRequests(authz -> authz
                // Permitir acceso sin autenticación a estos endpoints
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/health").permitAll()
                .requestMatchers("/api/info").permitAll()
                .requestMatchers("/api/welcome").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                
                // Temporalmente permitir acceso a API para pruebas de desarrollo
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/tasks/**").permitAll()
                
                // Permitir todo para desarrollo
                .anyRequest().permitAll()
            )
            
            // Deshabilitar autenticación básica para desarrollo
            // .httpBasic(basic -> {})
            
            // Deshabilitar formulario de login para desarrollo
            // .formLogin(form -> form
            //     .defaultSuccessUrl("/api/welcome", true)
            //     .permitAll()
            // )
            
            // Configurar logout
            .logout(logout -> logout
                .logoutSuccessUrl("/api/info")
                .permitAll()
            );

        return http.build();
    }
}
