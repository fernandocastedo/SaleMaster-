package org.example.salesmaster.mappers;

import org.example.salesmaster.dtos.factura.FacturaDTO;
import org.example.salesmaster.entities.Factura;

public class FacturaMapper {
    public static FacturaDTO mapFacturaToFacturaDTO(Factura factura) {
        return new FacturaDTO(
                factura.getIdFactura(),
                factura.getPedido() != null ? factura.getPedido().getIdPedido() : null,
                factura.getNro(),
                factura.getFecha(),
                factura.getTotal()
        );
    }

    public static Factura mapFacturaDTOToFactura(FacturaDTO facturaDTO) {
        Factura factura = new Factura();
        factura.setIdFactura(facturaDTO.getIdFactura());
        factura.setNro(facturaDTO.getNro());
        factura.setFecha(facturaDTO.getFecha());
        factura.setTotal(facturaDTO.getTotal());
        return factura;
    }
}

