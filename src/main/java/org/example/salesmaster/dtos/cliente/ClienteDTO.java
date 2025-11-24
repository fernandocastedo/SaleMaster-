package org.example.salesmaster.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String email;
}

