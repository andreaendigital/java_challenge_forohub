package com.aluracursos.forohub.dto;

import com.aluracursos.forohub.model.Topico;
import java.time.LocalDateTime;

// DTO para devolver los datos en la lista de tópicos.
public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion) {

    // Constructor para mapear desde la entidad Topico
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
    }
}