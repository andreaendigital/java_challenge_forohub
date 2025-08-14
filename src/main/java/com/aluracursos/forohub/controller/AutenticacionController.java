package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.dto.DatosAutenticacionUsuario;
import com.aluracursos.forohub.dto.DatosJWTToken;
import com.aluracursos.forohub.model.Usuario;
import com.aluracursos.forohub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        // Creamos un token de autenticación con el usuario y la contraseña
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.clave());
        // El AuthenticationManager verifica las credenciales con la BD
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        // Si son correctas, generamos el token JWT
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        // Devolvemos el token en la respuesta
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }
}