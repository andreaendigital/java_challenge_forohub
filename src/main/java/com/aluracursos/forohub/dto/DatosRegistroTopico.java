package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para recibir los datos al crear un nuevo tópico.
// Se usa anotaciones de Validation para las reglas de negocio.
public record DatosRegistroTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,
        @NotBlank(message = "El autor es obligatorio")
        String autor,
        @NotBlank(message = "El curso es obligatorio")
        String curso) {
}