package org.nebulabackend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "mensaje", "Backend Nebula ejecutándose correctamente",
                "servidor", "3.238.230.134:9080",
                "status", "OK",
                "timestamp", java.time.LocalDateTime.now().toString()
        );
    }

    @GetMapping("/index.html")
    public Map<String, String> index() {
        return Map.of(
                "mensaje", "Página de inicio de Nebula Backend",
                "status", "OK"
        );
    }
}