package com.aluracursos.forohub.model;


import com.aluracursos.forohub.dto.DatosActualizarTopico;
import com.aluracursos.forohub.dto.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos") // Mapea esta clase a la tabla "topicos"
@Entity(name = "Topico") // Es una entidad JPA
@Getter // Lombok: genera automáticamente los getters
@NoArgsConstructor // Lombok: genera un constructor sin argumentos (requerido por JPA)
@AllArgsConstructor // Lombok: genera un constructor con todos los argumentos
@EqualsAndHashCode(of = "id") // Lombok: genera equals y hashCode basados solo en el id
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID es autoincremental
    private Long id;

    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING) // El status será un Enum
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;

    private String autor;
    private String curso;

    // Constructor para crear un Topico a partir de un DTO
    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();
        // La fecha y el status se inicializan por defecto
    }

    // Método para actualizar los datos del tópico
    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.curso() != null) {
            this.curso = datosActualizarTopico.curso();
        }
    }
}