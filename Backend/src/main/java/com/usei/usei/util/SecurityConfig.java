package com.usei.usei.util;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs REST
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Usar nuestra config de CORS
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",
                    "/configuracion-seguridad/ping",
                    "/ping",
                    "/",
                    "/usuario/**",
                    "/rol/**",
                    "/imagenes/**",
                    "/documents/**"
                ).permitAll() // Permite acceso público a estas rutas
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
            )
            .httpBasic(basic -> basic.disable()); // Desactivar Basic Auth para evitar pop-ups del navegador

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        // IMPORTANTE: En producción (Railway), agrega aquí la URL de tu frontend si lo despliegas.
        // Por ahora, permitimos localhost y la propia URL de Railway del backend (para pruebas)
        config.setAllowedOrigins(List.of(
            "http://localhost:5173", 
            "https://usei-seguridad-back-production.up.railway.app"
        ));
        
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept"));
        config.setAllowCredentials(true); // Permitir cookies/credenciales si las usas

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}