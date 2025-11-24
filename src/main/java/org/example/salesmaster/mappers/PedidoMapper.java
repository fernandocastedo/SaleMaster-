package org.example.salesmaster.mappers;

import org.example.salesmaster.dtos.pedido.PedidoDTO;
import org.example.salesmaster.dtos.pedido.PedidoProductoDTO;
import org.example.salesmaster.entities.Pedido;
import org.example.salesmaster.entities.PedidoProducto;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    public static PedidoDTO mapPedidoToPedidoDTO(Pedido pedido) {
        List<PedidoProductoDTO> productosDTO = pedido.getPedidoProductos() != null
                ? pedido.getPedidoProductos().stream()
                        .map(PedidoMapper::mapPedidoProductoToDTO)
                        .collect(Collectors.toList())
                : List.of();

        return new PedidoDTO(
                pedido.getIdPedido(),
                pedido.getCliente() != null ? pedido.getCliente().getIdCliente() : null,
                pedido.getCliente() != null ? pedido.getCliente().getNombre() : null,
                pedido.getFecha(),
                pedido.getTotal(),
                productosDTO
        );
    }

    public static PedidoProductoDTO mapPedidoProductoToDTO(PedidoProducto pedidoProducto) {
        return new PedidoProductoDTO(
                pedidoProducto.getProducto().getIdProd(),
                pedidoProducto.getProducto().getNombre(),
                pedidoProducto.getCantidad(),
                pedidoProducto.getProducto().getPrecio(),
                pedidoProducto.getSubtotal()
        );
    }
}

