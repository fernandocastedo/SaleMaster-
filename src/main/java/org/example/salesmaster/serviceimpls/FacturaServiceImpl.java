package org.example.salesmaster.serviceimpls;

import org.example.salesmaster.dtos.factura.FacturaDTO;
import org.example.salesmaster.entities.Factura;
import org.example.salesmaster.entities.Pedido;
import org.example.salesmaster.exceptions.ResourceNotFoundException;
import org.example.salesmaster.mappers.FacturaMapper;
import org.example.salesmaster.repositories.FacturaRepository;
import org.example.salesmaster.repositories.PedidoRepository;
import org.example.salesmaster.services.FacturaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacturaServiceImpl implements FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        // Buscar pedido
        Pedido pedido = pedidoRepository.findById(facturaDTO.getIdPedido())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + facturaDTO.getIdPedido()));

        // Verificar si ya existe una factura para este pedido
        boolean existeFactura = facturaRepository.findAll().stream()
                .anyMatch(f -> f.getPedido().getIdPedido().equals(pedido.getIdPedido()));
        if (existeFactura) {
            throw new RuntimeException("Ya existe una factura para este pedido");
        }

        // Crear factura
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setNro(facturaDTO.getNro() != null ? facturaDTO.getNro() : generarNumeroFactura());
        factura.setFecha(facturaDTO.getFecha() != null ? facturaDTO.getFecha() : LocalDateTime.now());
        factura.calcularTotal();

        Factura savedFactura = facturaRepository.save(factura);
        return FacturaMapper.mapFacturaToFacturaDTO(savedFactura);
    }

    @Override
    @Transactional
    public FacturaDTO updateFactura(Long facturaId, FacturaDTO facturaDTO) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con id: " + facturaId));

        // Actualizar pedido si cambió
        if (!factura.getPedido().getIdPedido().equals(facturaDTO.getIdPedido())) {
            Pedido pedido = pedidoRepository.findById(facturaDTO.getIdPedido())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Pedido no encontrado con id: " + facturaDTO.getIdPedido()));
            factura.setPedido(pedido);
        }

        // Actualizar número de factura
        if (facturaDTO.getNro() != null && !facturaDTO.getNro().isBlank()) {
            factura.setNro(facturaDTO.getNro());
        }

        // Actualizar fecha
        if (facturaDTO.getFecha() != null) {
            factura.setFecha(facturaDTO.getFecha());
        }

        // Recalcular total
        factura.calcularTotal();

        Factura updatedFactura = facturaRepository.save(factura);
        return FacturaMapper.mapFacturaToFacturaDTO(updatedFactura);
    }

    @Override
    @Transactional
    public String deleteFactura(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con id: " + facturaId));
        facturaRepository.delete(factura);
        return "Factura ha sido eliminada";
    }

    @Override
    public FacturaDTO getFactura(Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con id: " + facturaId));
        return FacturaMapper.mapFacturaToFacturaDTO(factura);
    }

    @Override
    public List<FacturaDTO> getFacturas() {
        List<Factura> facturas = facturaRepository.findAll();
        return facturas.stream()
                .map(FacturaMapper::mapFacturaToFacturaDTO)
                .collect(Collectors.toList());
    }

    /**
     * Genera un número de factura único
     */
    private String generarNumeroFactura() {
        long count = facturaRepository.count();
        return "FAC-" + String.format("%06d", count + 1);
    }
}

