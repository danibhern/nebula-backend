package org.nebulabackend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no se puede extender a 50 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 9, message = "El teléfono no puede exceder 9 caracteres")
    private String telefono;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 100, message = "El asunto no puede exceder 100 caracteres")
    private String asunto;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;

    }


}