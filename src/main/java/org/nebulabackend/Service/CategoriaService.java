package org.nebulabackend.Service;

import org.nebulabackend.Model.Categoria;
import org.nebulabackend.Repository.CategoriaRepository;
import org.nebulabackend.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Categoria save(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }

        String nombreNormalizado = categoria.getNombre().trim();

        Categoria categoriaExistente = categoriaRepository.findByNombre(nombreNormalizado);

        if (categoriaExistente != null) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + nombreNormalizado);
        }
        categoria.setNombre(nombreNormalizado);

        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, Categoria categoriaDetails) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            Categoria categoriaExistente = categoriaOptional.get();

            if (categoriaDetails.getNombre() == null || categoriaDetails.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo ni vacío.");
            }

            String nuevoNombre = categoriaDetails.getNombre().trim();

            Categoria otraCategoriaExistente = categoriaRepository.findByNombre(nuevoNombre);
            if (otraCategoriaExistente != null && !otraCategoriaExistente.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + nuevoNombre);
            }

            categoriaExistente.setNombre(nuevoNombre);

            return categoriaRepository.save(categoriaExistente);
        } else {
            return null;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return false; // No existe la categoría
        }
        long productosAsociados = productoRepository.countByCategoriaId(id);

        if (productosAsociados > 0) {
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene " + productosAsociados + " productos asociados.");
        }

        categoriaRepository.deleteById(id);
        return true;
    }
}