package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Factura Entity
 * -----------------------------------------------------
 * ✔ Representa una factura del sistema SalesMaster
 * ✔ Según modelo lógico: FACTURA(id_factura, id_pedido FK, nro, fecha, total)
 */
@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "nro", nullable = false, length = 15, unique = true)
    private String nro;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    /**
     * Calcula el total de la factura basado en el total del pedido
     */
    public void calcularTotal() {
        if (pedido != null && pedido.getTotal() != null) {
            this.total = pedido.getTotal();
        } else {
            this.total = BigDecimal.ZERO;
        }
    }
}

