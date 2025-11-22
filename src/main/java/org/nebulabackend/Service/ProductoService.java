package org.nebulabackend.Service;

import org.nebulabackend.Model.Producto;
import org.nebulabackend.Model.Categoria;
import org.nebulabackend.Repository.ProductoRepository;
import org.nebulabackend.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> findByCategoria(Long categoriaId) {

        if (categoriaId == null || categoriaId <= 0) {
            return productoRepository.findAll();
        }
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Transactional
    public Producto save(Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser positivo.");
        }

        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("El producto debe tener una categoría asignada.");
        }

        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NoSuchElementException("Categoría con ID " + categoriaId + " no encontrada."));

        producto.setCategoria(categoria);

        if (producto.getStock() < 0) {
            producto.setStock(0);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto update(Long id, Producto productoDetails) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoOptional.isPresent()) {
            Producto productoExistente = productoOptional.get();

            if (productoDetails.getPrecio() != 0 && productoDetails.getPrecio() <= 0) {
                throw new IllegalArgumentException("El precio de actualización debe ser positivo.");
            }

            if (productoDetails.getNombre() != null && !productoDetails.getNombre().trim().isEmpty()) {
                productoExistente.setNombre(productoDetails.getNombre().trim());
            }

            if (productoDetails.getDescripcion() != null) {
                productoExistente.setDescripcion(productoDetails.getDescripcion());
            }
            if (productoDetails.getPrecio() != 0) {
                productoExistente.setPrecio(productoDetails.getPrecio());
            }

            if (productoDetails.getStock() != -1 && productoDetails.getStock() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo.");
            } else if (productoDetails.getStock() >= 0) {
                productoExistente.setStock(productoDetails.getStock());
            }

            if (productoDetails.getCategoria() != null && productoDetails.getCategoria().getId() != null) {
                Long newCategoriaId = productoDetails.getCategoria().getId();
                Categoria categoria = categoriaRepository.findById(newCategoriaId)
                        .orElseThrow(() -> new NoSuchElementException("Nueva categoría con ID " + newCategoriaId + " no encontrada."));

                productoExistente.setCategoria(categoria);
            }

            return productoRepository.save(productoExistente);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}