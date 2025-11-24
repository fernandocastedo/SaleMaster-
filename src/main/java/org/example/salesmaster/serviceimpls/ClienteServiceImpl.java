package org.example.salesmaster.serviceimpls;

import org.example.salesmaster.dtos.cliente.ClienteDTO;
import org.example.salesmaster.entities.Cliente;
import org.example.salesmaster.exceptions.ResourceNotFoundException;
import org.example.salesmaster.mappers.ClienteMapper;
import org.example.salesmaster.repositories.ClienteRepository;
import org.example.salesmaster.services.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.mapClienteDTOToCliente(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return ClienteMapper.mapClienteToClienteDTO(savedCliente);
    }

    @Override
    public ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId)
        );
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());

        Cliente updatedCliente = clienteRepository.save(cliente);
        return ClienteMapper.mapClienteToClienteDTO(updatedCliente);
    }

    @Override
    public String deleteCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId)
        );
        clienteRepository.delete(cliente);
        return "Cliente ha sido eliminado";
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId)
        );
        return ClienteMapper.mapClienteToClienteDTO(cliente);
    }

    @Override
    public List<ClienteDTO> getClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteMapper::mapClienteToClienteDTO)
                .collect(Collectors.toList());
    }
}

