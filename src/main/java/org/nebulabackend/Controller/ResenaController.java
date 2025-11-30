package org.nebulabackend.Controller;

import org.nebulabackend.Model.Resena;
import org.nebulabackend.Service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public ResponseEntity<List<Resena>> getAllResenas(){
        try{
            List<Resena> resenas =resenaService.getAllResenas();
            return new ResponseEntity<>(resenas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        try {
            Optional<Resena> resena = resenaService.getResenaById(id);
            if (resena.isPresent()) {
                return new ResponseEntity<>(resena.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear nueva reseña
    @PostMapping
    public ResponseEntity<Resena> createResena(@RequestBody Resena resena) {
        try {
            Resena savedResena = resenaService.saveResena(resena);
            return new ResponseEntity<>(savedResena, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteResena(@PathVariable Long id) {
        try {
            Optional<Resena> resena = resenaService.getResenaById(id);
            if (resena.isPresent()) {
                resenaService.deleteResena(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calificacion/{calificacion}")
    public ResponseEntity<List<Resena>> getResenasByCalificacion(@PathVariable Integer calificacion) {
        try {
            List<Resena> resenas = resenaService.getResenasByCalificacion(calificacion);
            return new ResponseEntity<>(resenas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
