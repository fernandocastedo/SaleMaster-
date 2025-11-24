package org.example.salesmaster.mappers;

import org.example.salesmaster.dtos.producto.ProductoDTO;
import org.example.salesmaster.entities.Producto;

public class ProductoMapper {
    public static ProductoDTO mapProductoToProductoDTO(Producto producto) {
        return new ProductoDTO(
                producto.getIdProd(),
                producto.getNombre(),
                producto.getPrecio()
        );
    }

    public static Producto mapProductoDTOToProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setIdProd(productoDTO.getIdProd());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        return producto;
    }
}

