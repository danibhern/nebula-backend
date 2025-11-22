package org.nebulabackend.Repository;

import org.nebulabackend.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByNombre(String nombre);
    long countByCategoriaId(Long categoriaId);
    List<Producto> findByCategoriaId(Long categoriaId);
}
