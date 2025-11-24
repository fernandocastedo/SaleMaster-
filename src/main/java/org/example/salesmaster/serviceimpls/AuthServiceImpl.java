package org.example.salesmaster.serviceimpls;

import org.example.salesmaster.dtos.auth.AuthenticationRequest;
import org.example.salesmaster.dtos.auth.AuthenticationResponse;
import org.example.salesmaster.dtos.auth.RegisterRequest;
import org.example.salesmaster.entities.Role;
import org.example.salesmaster.entities.User;
import org.example.salesmaster.repositories.UserRepository;
import org.example.salesmaster.security.JwtService;
import org.example.salesmaster.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl
 * -----------------------------------------------------
 * ✔ Gestiona registro y autenticación de usuarios
 * ✔ Genera tokens JWT válidos con roles incluidos
 * ✔ Retorna la respuesta de autenticación al frontend
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * ✅ Registrar un nuevo usuario
     */
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();

        // Evitar errores de null en firstname / lastname
        user.setFirstname(
                request.getFirstname() != null && !request.getFirstname().isBlank()
                        ? request.getFirstname()
                        : "User"
        );
        user.setLastname(
                request.getLastname() != null && !request.getLastname().isBlank()
                        ? request.getLastname()
                        : "Default"
        );

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Determinar el rol: si viene en el request, usarlo; sino, por defecto VENDEDOR
        if (request.getRole() != null && !request.getRole().isBlank()) {
            try {
                user.setRole(Role.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.VENDEDOR); // Default si el rol no es válido
            }
        } else {
            user.setRole(Role.VENDEDOR); // Default
        }

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole().name())
                .build();
    }

    /**
     * ✅ Autenticar usuario existente
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("❌ Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole().name())
                .build();
    }
}

