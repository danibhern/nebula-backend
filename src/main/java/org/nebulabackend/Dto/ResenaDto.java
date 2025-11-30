package org.nebulabackend.Dto;

import jakarta.validation.constraints.*;

public class ResenaDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer calificacion;

    @NotBlank(message = "La reseña es obligatoria")
    @Size(min = 10, max = 500, message = "La reseña debe tener entre 10 y 500 caracteres")
    private String resena;

    // Constructores
    public ResenaDto() {}

    public ResenaDto(String nombre, Integer calificacion, String resena) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.resena = resena;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }
}
