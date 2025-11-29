package org.nebulabackend.Repository;

import org.nebulabackend.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    List<Reserva> findByEmail(String email);
    List<Reserva> findByFecha(LocalDate fecha);
    List<Reserva> findByFechaAndHora(LocalDate fecha, LocalTime hora);
}
