package org.nebulabackend.Controller;


import jakarta.validation.Valid;
import org.nebulabackend.Dto.ReservaDto;
import org.nebulabackend.Model.Reserva;
import org.nebulabackend.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<?>crearReserva(@Valid @RequestBody ReservaDto reservaDto){
        try {
            Reserva reservaCreada = reservaService.crearReserva(reservaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reserva>>obtenerTodasRserva(){
        List<Reserva> reservas =reservaService.obtenerTodasRserva();
        return ResponseEntity.ok(reservas);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id,@Valid @RequestBody ReservaDto reservaDto) {
        try {
            Reserva reservaActualizada = reservaService.actualizarReserva(id, reservaDto);
            return ResponseEntity.ok(reservaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    // DELETE: Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long id) {
        try {
            reservaService.eliminarReserva(id);
            return ResponseEntity.ok().body("Reserva eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }





}
