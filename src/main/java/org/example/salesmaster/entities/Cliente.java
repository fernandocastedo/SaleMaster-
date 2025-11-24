package org.example.salesmaster.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cliente Entity
 * -----------------------------------------------------
 * ✔ Representa un cliente del sistema SalesMaster
 * ✔ Según modelo lógico: CLIENTE(id_cliente, nombre, email)
 */
@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
}

