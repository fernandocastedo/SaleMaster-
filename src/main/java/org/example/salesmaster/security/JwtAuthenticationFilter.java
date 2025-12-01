package org.example.salesmaster.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter
 * -----------------------------------------------------
 * ✔ Intercepta todas las solicitudes HTTP
 * ✔ Extrae y valida el token JWT del encabezado Authorization
 * ✔ Autentica al usuario si el token es válido
 * ✔ Permite pasar la solicitud al siguiente filtro en la cadena
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // ✅ Permitir peticiones OPTIONS (preflight de CORS) sin procesar
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;

        // 🔍 Si no hay cabecera o no empieza con "Bearer ", continuar sin procesar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🧾 Extraer el token JWT (sin la palabra "Bearer ")
        jwt = authHeader.substring(7);

        try {
            // 👤 Extraer usuario desde el token
            username = jwtService.extractUsername(jwt);

            // 🔐 Validar token si aún no hay autenticación en contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // ✅ Validar correctamente con el objeto UserDetails
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // ❌ Token inválido o expirado - continuar sin autenticación
            // El SecurityFilterChain se encargará de rechazar la petición si es necesario
            logger.error("Error al procesar token JWT: " + e.getMessage());
        }

        // 🚀 Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}

