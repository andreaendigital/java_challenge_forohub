package com.aluracursos.forohub.repository;

import com.aluracursos.forohub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

// Hereda de JpaRepository para obtener los métodos CRUD básicos.
// <Topico, Long> indica que manejará la entidad Topico, cuya clave primaria es de tipo Long.

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Metodo para buscar un tópico por título y mensaje para evitar duplicados.
    // Spring Data JPA crea la query automáticamente a partir del nombre del metodo.

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}