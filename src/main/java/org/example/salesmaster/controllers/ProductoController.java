package org.example.salesmaster.controllers;

import org.example.salesmaster.dtos.producto.ProductoDTO;
import org.example.salesmaster.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    // localhost:8080/api/productos
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO savedProductoDTO = productoService.createProducto(productoDTO);
        return new ResponseEntity<>(savedProductoDTO, HttpStatus.CREATED);
    }

    // localhost:8080/api/productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoDTO> productos = productoService.getProductos();
        return ResponseEntity.ok(productos);
    }

    // localhost:8080/api/productos/1
    @GetMapping("{id}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.getProducto(id);
        return ResponseEntity.ok(productoDTO);
    }

    // localhost:8080/api/productos/1
    @PutMapping("{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id,
                                                       @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProductoDTO = productoService.updateProducto(id, productoDTO);
        return ResponseEntity.ok(updatedProductoDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.ok("Producto eliminado exitosamente !!!.");
    }
}

