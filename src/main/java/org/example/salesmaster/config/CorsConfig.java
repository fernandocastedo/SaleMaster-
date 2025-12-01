package org.example.salesmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Usar setAllowedOriginPatterns en lugar de setAllowedOrigins cuando se usa allowCredentials
        // Esto evita problemas de compatibilidad con Spring Security
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:*", // Permite cualquier puerto de localhost
                "http://127.0.0.1:*"  // Permite cualquier puerto de 127.0.0.1
        ));

        // ✅ Métodos permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

        // ✅ Headers permitidos (incluir todos los necesarios)
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // ✅ Permitir credenciales (necesario para JWT)
        config.setAllowCredentials(true);

        // ✅ Exponer headers en la respuesta
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));

        // ✅ Max age para preflight (en segundos)
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}

