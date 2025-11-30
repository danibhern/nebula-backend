package org.nebulabackend.Repository;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.nebulabackend.Model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResenaRepository extends JpaRepository<Resena,Long> {
    List<Resena> findByCalificacion(Integer calificacion);
}
