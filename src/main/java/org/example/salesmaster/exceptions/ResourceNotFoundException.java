package org.example.salesmaster.exceptions;

/**
 * 🚫 ResourceNotFoundException
 * ---------------------------------------------------------
 * ✅ Se lanza cuando un recurso solicitado no existe en la base de datos.
 * ✅ Centraliza la comunicación de errores 404 (Not Found).
 * ✅ Compatible con GlobalExceptionHandler para devolver respuestas JSON uniformes.
 *
 * Ejemplo de uso:
 * ---------------------------------------------------------
 * clienteRepository.findById(id)
 *      .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor extendido (opcional) con causa original.
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

