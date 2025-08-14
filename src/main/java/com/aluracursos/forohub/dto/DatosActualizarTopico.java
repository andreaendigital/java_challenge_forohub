package com.aluracursos.forohub.dto;

import jakarta.validation.constraints.NotNull;

// DTO para recibir los datos al actualizar un tópico.
// No usamos @NotBlank porque los campos son opcionales en la actualización.
public record DatosActualizarTopico(
        String titulo,
        String mensaje,
        String curso) {
}