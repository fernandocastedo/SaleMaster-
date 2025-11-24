package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * PedidoProducto Entity
 * -----------------------------------------------------
 * ✔ Tabla intermedia entre Pedido y Producto
 * ✔ Según modelo lógico: PEDIDO_PRODUCTO(id_pedido FK, id_prod FK, cantidad, subtotal)
 */
@Entity
@Table(name = "pedido_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProducto {
    @EmbeddedId
    private PedidoProductoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProd")
    @JoinColumn(name = "id_prod")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    /**
     * Calcula el subtotal basado en cantidad y precio del producto
     */
    public void calcularSubtotal() {
        if (producto != null && cantidad != null && producto.getPrecio() != null) {
            this.subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        }
    }
}

