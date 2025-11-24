package org.example.salesmaster.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * PedidoProductoId
 * -----------------------------------------------------
 * ✔ Clave primaria compuesta para PedidoProducto
 * ✔ PK(id_pedido, id_prod)
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoProductoId implements Serializable {
    private Long idPedido;
    private Long idProd;
}

