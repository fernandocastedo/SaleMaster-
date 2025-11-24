package org.example.salesmaster.dtos.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PedidoDTO {
    private Long idPedido;
    private Long idCliente;
    private String nombreCliente;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<PedidoProductoDTO> productos;
}

