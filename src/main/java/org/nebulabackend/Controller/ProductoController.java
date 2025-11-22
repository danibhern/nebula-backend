package org.nebulabackend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nebulabackend.Model.Producto;
import org.nebulabackend.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Tag(name = "Gestión de Productos", description = "CRUD para el catálogo de productos de Nebula.")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    // --- CRUD: READ ALL (Acceso público) ---
    @Operation(summary = "Obtener todos los productos", description = "Lista todos los productos del catálogo. No requiere autenticación.")
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    // --- CRUD: READ BY ID (Acceso público) ---
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su id.")
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.findById(id);

        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- NUEVO ENDPOINT: FILTRAR POR CATEGORÍA (Acceso público) ---
    @Operation(summary = "Filtrar productos por categoría", description = "Obtiene productos asociados a un ID de categoría específica. Si el ID no se proporciona, lista todos.")
    @GetMapping("/categoria")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Producto>> getProductosByCategoria(@RequestParam(required = false) Long categoriaId) {
        List<Producto> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }

    // --- CRUD: CREATE (SOLO ADMIN) ---
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto. Requiere el rol ADMIN.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.save(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- CRUD: UPDATE (SOLO ADMIN) ---
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente por su ID. Requiere el rol ADMIN.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        try {
            Producto productoActualizado = productoService.update(id, productoDetails);

            if (productoActualizado != null) {
                return ResponseEntity.ok(productoActualizado);
            } else {
                return ResponseEntity.notFound().build(); // Producto ID no encontrado
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- CRUD: DELETE (SOLO ADMIN) ---
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID. Requiere el rol ADMIN.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        boolean eliminado = productoService.delete(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}