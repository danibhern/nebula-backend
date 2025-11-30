package org.nebulabackend.Service;

import org.nebulabackend.Dto.ReservaDto;
import org.nebulabackend.Model.Reserva;
import org.nebulabackend.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;


    public List<Reserva> obtenerTodasRserva(){
        return reservaRepository.findAll();
    }

    public List<Reserva>obtenerReservaporEmail(String email){
        return reservaRepository.findByEmail(email);
    }

    public Reserva actualizarReserva(Long id, ReservaDto reservaDto) {
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);

        if (reservaExistente.isPresent()) {
            Reserva reserva = reservaExistente.get();
            reserva.setNombre(reservaDto.getNombre());
            reserva.setEmail(reservaDto.getEmail());
            reserva.setTelefono(reservaDto.getTelefono());
            reserva.setFecha(reservaDto.getFecha());
            reserva.setHora(reservaDto.getHora());
            reserva.setNumPersonas(reservaDto.getNumPersonas());

            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
    }

    public void eliminarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
    }

    // Verificar disponibilidad
    private boolean estaDisponible(LocalDate fecha, LocalTime hora) {
        List<Reserva> reservasExistentes = reservaRepository.findByFechaAndHora(fecha, hora);
        return reservasExistentes.isEmpty(); // True si no hay reservas en ese horario
    }

    // Crear nueva reserva
    public Reserva crearReserva(ReservaDto reservaDto) {
        // Verificar disponibilidad
        if (!estaDisponible(reservaDto.getFecha(), reservaDto.getHora())) {
            throw new RuntimeException("No hay disponibilidad para la fecha y hora seleccionada");
        }

        // Convertir DTO a Entity
        Reserva reserva = new Reserva();
        reserva.setNombre(reservaDto.getNombre());
        reserva.setEmail(reservaDto.getEmail());
        reserva.setTelefono(reservaDto.getTelefono());
        reserva.setFecha(reservaDto.getFecha());
        reserva.setHora(reservaDto.getHora());
        reserva.setNumPersonas(reservaDto.getNumPersonas());

        return reservaRepository.save(reserva);
    }



}
