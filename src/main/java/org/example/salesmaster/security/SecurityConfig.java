package org.example.salesmaster.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.http.HttpMethod;

/**
 * SecurityConfig
 * -----------------------------------------------------
 * ✔ Configura Spring Security con JWT
 * ✔ Deshabilita CSRF (porque usamos token)
 * ✔ Aplica CORS global desde CorsConfig
 * ✔ Protege rutas excepto /api/auth/**
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Habilitar CORS global (configurado en CorsConfig)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // ❌ Desactivar CSRF (no se usa con JWT)
                .csrf(AbstractHttpConfigurer::disable)
                // ✅ Definir rutas públicas y protegidas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                // Swagger UI
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api/auth/me").authenticated()
                        // Permitir peticiones OPTIONS (preflight de CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )

                // ✅ Política de sesión sin estado
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // ✅ Registrar el AuthenticationProvider
                .authenticationProvider(authenticationProvider)
                // ✅ Registrar el filtro CORS antes del filtro JWT (orden importante)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // ✅ Registrar el filtro JWT antes del UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

