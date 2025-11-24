package org.example.salesmaster.services;

import org.example.salesmaster.dtos.cliente.ClienteDTO;

import java.util.List;

public interface ClienteService {
    ClienteDTO createCliente(ClienteDTO clienteDTO);
    ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO);
    String deleteCliente(Long clienteId);
    ClienteDTO getCliente(Long clienteId);
    List<ClienteDTO> getClientes();
}

