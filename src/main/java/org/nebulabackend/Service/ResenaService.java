package org.nebulabackend.Service;

import org.nebulabackend.Model.Resena;
import org.nebulabackend.Repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resena> getAllResenas() {
        return resenaRepository.findAll();
    }

    // Obtener reseña por ID
    public Optional<Resena> getResenaById(Long id) {
        return resenaRepository.findById(id);
    }

    // Guardar reseña
    public Resena saveResena(Resena resena) {
        return resenaRepository.save(resena);
    }

    public void deleteResena(Long id) {
        resenaRepository.deleteById(id);
    }

    // Obtener reseñas por calificación
    public List<Resena> getResenasByCalificacion(Integer calificacion) {
        return resenaRepository.findByCalificacion(calificacion);
    }



}
