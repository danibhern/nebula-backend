package org.nebulabackend.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nebulabackend.Model.Categoria;
import org.nebulabackend.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Tag(name = "Gestión de Categorías", description = "Endpoints para manejar las categorías de productos (ej: Cafes Premium, Insumos).")
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // =========================================================
    // CRUD: READ (Lectura)
    // =========================================================

    @Operation(summary = "Obtener todas las categorías", description = "Lista todas las categorías disponibles. Acceso público.")
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Busca una categoría específica por su identificador. Acceso público.")
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.findById(id);

        return categoria.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================================================
    // CRUD: CREATE (Creación) - SOLO ADMIN
    // =========================================================

    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría. Requiere el rol ADMIN.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = categoriaService.save(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // =========================================================
    // CRUD: UPDATE (Actualización) - SOLO ADMIN
    // =========================================================

    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente. Requiere el rol ADMIN. Lanza 404 si no existe o 400 si el nombre está duplicado.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        try {
            Categoria categoriaActualizada = categoriaService.update(id, categoriaDetails);

            if (categoriaActualizada != null) {
                return ResponseEntity.ok(categoriaActualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // =========================================================
    // CRUD: DELETE (Eliminación) - SOLO ADMIN
    // =========================================================

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por ID. Requiere el rol ADMIN. Lanza 409 si tiene productos asociados.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        boolean eliminado = categoriaService.delete(id);

        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}