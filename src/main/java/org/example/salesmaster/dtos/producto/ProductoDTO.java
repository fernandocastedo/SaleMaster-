package org.example.salesmaster.dtos.producto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoDTO {
    private Long idProd;
    private String nombre;
    private BigDecimal precio;
}

