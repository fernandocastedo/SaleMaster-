package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Producto Entity
 * -----------------------------------------------------
 * ✔ Representa un producto del sistema SalesMaster
 * ✔ Según modelo lógico: PRODUCTO(id_prod, nombre, precio)
 */
@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prod")
    private Long idProd;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}

