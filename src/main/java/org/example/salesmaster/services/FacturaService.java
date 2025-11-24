package org.example.salesmaster.services;

import org.example.salesmaster.dtos.factura.FacturaDTO;

import java.util.List;

public interface FacturaService {
    FacturaDTO createFactura(FacturaDTO facturaDTO);
    FacturaDTO updateFactura(Long facturaId, FacturaDTO facturaDTO);
    String deleteFactura(Long facturaId);
    FacturaDTO getFactura(Long facturaId);
    List<FacturaDTO> getFacturas();
}

