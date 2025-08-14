package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dto.*;
import com.aluracursos.forohub.model.Topico;
import com.aluracursos.forohub.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController // Indica que es un controlador REST
@RequestMapping("/topicos") // Todas las rutas de este controlador empezarán con /topicos
public class TopicoController {

    @Autowired // Inyección de dependencias: Spring instancia el repositorio por nosotros
    private TopicoRepository topicoRepository;

    // --- Endpoint para CREAR un nuevo tópico (CREATE) ---
    @PostMapping
    @Transactional // Indica que es una transacción a la base de datos
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        // Validación para no permitir tópicos duplicados
        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            // Si ya existe, devolvemos un error 409 Conflict
            return ResponseEntity.status(409).build();
        }

        // Creamos la entidad Tópico a partir del DTO
        Topico topico = new Topico(datosRegistroTopico);
        // Guardamos el tópico en la base de datos
        topicoRepository.save(topico);

        // Creamos la URI para la respuesta, siguiendo las buenas prácticas de REST
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        // Devolvemos una respuesta 201 Created, con la URL del nuevo recurso y los datos del tópico creado
        return ResponseEntity.created(url).body(new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        ));
    }

    // --- Endpoint para LISTAR todos los tópicos (READ) ---
    // Opcional: con paginación y ordenamiento
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        // Buscamos todos los tópicos y los mapeamos a nuestro DTO de listado
        Page<DatosListadoTopico> pagina = topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
        return ResponseEntity.ok(pagina);
    }

    // --- Endpoint para DETALLAR un tópico específico (READ by ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {
        // Buscamos el tópico por ID
        Topico topico = topicoRepository.getReferenceById(id);
        // Devolvemos los datos completos del tópico
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
        return ResponseEntity.ok(datosTopico);
    }

    // --- Endpoint para ACTUALIZAR un tópico (UPDATE) ---
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        // Buscamos el tópico por ID
        Topico topico = topicoRepository.getReferenceById(id);
        // Actualizamos los datos del tópico con la información del DTO
        topico.actualizarDatos(datosActualizarTopico);
        // Devolvemos los datos actualizados
        return ResponseEntity.ok(new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        ));
    }

    // --- Endpoint para ELIMINAR un tópico (DELETE) ---
    // Este es un borrado lógico, no físico.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        // Buscamos el tópico por ID
        Topico topico = topicoRepository.getReferenceById(id);
        // En lugar de borrarlo, podríamos cambiar su status a CERRADO o INACTIVO.
        // topico.setStatus(StatusTopico.CERRADO);
        // O si queremos un borrado físico:
        topicoRepository.delete(topico);
        // Devolvemos una respuesta 204 No Content, indicando que se procesó correctamente
        return ResponseEntity.noContent().build();
    }
}