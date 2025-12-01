package org.example.salesmaster.serviceimpls;

import org.example.salesmaster.dtos.producto.ProductoDTO;
import org.example.salesmaster.entities.Producto;
import org.example.salesmaster.exceptions.ResourceNotFoundException;
import org.example.salesmaster.mappers.ProductoMapper;
import org.example.salesmaster.repositories.ProductoRepository;
import org.example.salesmaster.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    public ProductoDTO createProducto(ProductoDTO productoDTO) {
        Producto producto = ProductoMapper.mapProductoDTOToProducto(productoDTO);
        Producto savedProducto = productoRepository.save(producto);
        return ProductoMapper.mapProductoToProductoDTO(savedProducto);
    }

    @Override
    public ProductoDTO updateProducto(Long productoId, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId)
        );
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());

        Producto updatedProducto = productoRepository.save(producto);
        return ProductoMapper.mapProductoToProductoDTO(updatedProducto);
    }

    @Override
    public String deleteProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId)
        );
        productoRepository.delete(producto);
        return "Producto ha sido eliminado";
    }

    @Override
    public ProductoDTO getProducto(Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado con id: " + productoId)
        );
        return ProductoMapper.mapProductoToProductoDTO(producto);
    }

    @Override
    public List<ProductoDTO> getProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::mapProductoToProductoDTO)
                .collect(Collectors.toList());
    }
}

