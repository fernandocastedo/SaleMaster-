package org.example.salesmaster.services;

import org.example.salesmaster.dtos.producto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    ProductoDTO createProducto(ProductoDTO productoDTO);
    ProductoDTO updateProducto(Long productoId, ProductoDTO productoDTO);
    String deleteProducto(Long productoId);
    ProductoDTO getProducto(Long productoId);
    List<ProductoDTO> getProductos();
}

