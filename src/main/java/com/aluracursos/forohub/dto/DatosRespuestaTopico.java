package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.model.StatusTopico;
import java.time.LocalDateTime;

// DTO para devolver una respuesta completa de un tópico (GET por ID, POST, PUT)
public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String autor,
        String curso) {
}