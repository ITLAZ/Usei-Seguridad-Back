package com.usei.usei;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Permitir acceso desde CUALQUIER lugar (Ideal para demo, usar con cuidado en producción real)
        config.setAllowedOrigins(Collections.singletonList("*"));

        // 2. Permitir TODOS los headers (Esto arregla el error de "header not allowed")
        config.setAllowedHeaders(Collections.singletonList("*"));

        // 3. Permitir TODOS los métodos HTTP
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 4. Credenciales deben ser false si usas "*" en origins
        config.setAllowCredentials(false);

        // 5. Cachear la respuesta preflight para que no pregunte en cada petición (1 hora)
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}