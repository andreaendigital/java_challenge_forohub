package com.aluracursos.forohub;

import com.aluracursos.forohub.model.Usuario;
import com.aluracursos.forohub.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ForohubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForohubApplication.class, args);
	}

	// Este metodo se ejecutará una sola vez al iniciar la aplicación
	@Bean
	public CommandLineRunner commandLineRunner(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Verificar si el usuario 'admin' ya existe
			if (repository.findByLogin("admin") == null) {
				// Si no existe, lo crea
				Usuario adminUser = new Usuario(null, "admin", passwordEncoder.encode("123456"));
				repository.save(adminUser);
				System.out.println(">>> Usuario 'admin' creado con contraseña '123456'");
			}
		};
	}
}