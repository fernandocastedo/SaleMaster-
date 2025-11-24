package org.example.salesmaster.mappers;

import org.example.salesmaster.dtos.cliente.ClienteDTO;
import org.example.salesmaster.entities.Cliente;

public class ClienteMapper {
    public static ClienteDTO mapClienteToClienteDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getEmail()
        );
    }

    public static Cliente mapClienteDTOToCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(clienteDTO.getIdCliente());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        return cliente;
    }
}

