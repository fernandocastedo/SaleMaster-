package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Pedido Entity
 * -----------------------------------------------------
 * ✔ Representa un pedido del sistema SalesMaster
 * ✔ Según modelo lógico: PEDIDO(id_pedido, id_cliente FK, fecha, total)
 */
@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidoProductos = new ArrayList<>();

    /**
     * Calcula el total del pedido sumando todos los subtotales
     */
    public void calcularTotal() {
        if (pedidoProductos != null && !pedidoProductos.isEmpty()) {
            this.total = pedidoProductos.stream()
                    .map(pp -> pp.getSubtotal() != null ? pp.getSubtotal() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            this.total = BigDecimal.ZERO;
        }
    }

    /**
     * Agrega un producto al pedido
     */
    public void agregarProducto(Producto producto, Integer cantidad) {
        PedidoProducto pedidoProducto = new PedidoProducto();
        PedidoProductoId id = new PedidoProductoId(this.idPedido, producto.getIdProd());
        pedidoProducto.setId(id);
        pedidoProducto.setPedido(this);
        pedidoProducto.setProducto(producto);
        pedidoProducto.setCantidad(cantidad);
        pedidoProducto.calcularSubtotal();
        this.pedidoProductos.add(pedidoProducto);
    }
}

