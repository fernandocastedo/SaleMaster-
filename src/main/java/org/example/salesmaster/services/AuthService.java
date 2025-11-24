package org.example.salesmaster.services;

import org.example.salesmaster.dtos.auth.AuthenticationRequest;
import org.example.salesmaster.dtos.auth.AuthenticationResponse;
import org.example.salesmaster.dtos.auth.RegisterRequest;

/**
 * AuthService
 * -----------------------------------------------------
 * ✔ Define las operaciones públicas del servicio de autenticación
 */
public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

