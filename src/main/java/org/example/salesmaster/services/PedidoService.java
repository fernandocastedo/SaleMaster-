package org.example.salesmaster.services;

import org.example.salesmaster.dtos.pedido.PedidoDTO;

import java.util.List;

public interface PedidoService {
    PedidoDTO createPedido(PedidoDTO pedidoDTO);
    PedidoDTO updatePedido(Long pedidoId, PedidoDTO pedidoDTO);
    String deletePedido(Long pedidoId);
    PedidoDTO getPedido(Long pedidoId);
    List<PedidoDTO> getPedidos();
}

