package org.example.salesmaster.controllers;

import org.example.salesmaster.dtos.factura.FacturaDTO;
import org.example.salesmaster.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin("*")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    // localhost:8080/api/facturas
    @PostMapping
    public ResponseEntity<FacturaDTO> createFactura(@RequestBody FacturaDTO facturaDTO) {
        FacturaDTO savedFacturaDTO = facturaService.createFactura(facturaDTO);
        return new ResponseEntity<>(savedFacturaDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/facturas
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturaService.getFacturas();
        return ResponseEntity.ok(facturas);
    }

    // localhost:8080/api/facturas/1
    @GetMapping("{id}")
    public ResponseEntity<FacturaDTO> getFactura(@PathVariable Long id) {
        FacturaDTO facturaDTO = facturaService.getFactura(id);
        return ResponseEntity.ok(facturaDTO);
    }

    // localhost:8080/api/facturas/1
    @PutMapping("{id}")
    public ResponseEntity<FacturaDTO> updateFactura(@PathVariable Long id,
                                                     @RequestBody FacturaDTO facturaDTO) {
        FacturaDTO updatedFacturaDTO = facturaService.updateFactura(id, facturaDTO);
        return ResponseEntity.ok(updatedFacturaDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFactura(@PathVariable Long id) {
        facturaService.deleteFactura(id);
        return ResponseEntity.ok("Factura eliminada exitosamente !!!.");
    }
}

