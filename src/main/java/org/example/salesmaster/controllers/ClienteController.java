package org.example.salesmaster.controllers;

import org.example.salesmaster.dtos.cliente.ClienteDTO;
import org.example.salesmaster.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    // localhost:8080/api/clientes
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO savedClienteDTO = clienteService.createCliente(clienteDTO);
        return new ResponseEntity<>(savedClienteDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    // localhost:8080/api/clientes/1
    @GetMapping("{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.getCliente(id);
        return ResponseEntity.ok(clienteDTO);
    }

    // localhost:8080/api/clientes/1
    @PutMapping("{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable Long id,
                                                     @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO updatedClienteDTO = clienteService.updateCliente(id, clienteDTO);
        return ResponseEntity.ok(updatedClienteDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok("Cliente eliminado exitosamente !!!.");
    }
}

