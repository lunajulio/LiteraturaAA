//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.alura.LiteraturA.principal.Principal;
import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;

@SpringBootApplication
public class LiteraturAApplication  {
	
	public LiteraturAApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturAApplication.class, args);
	}

	@Bean
    @Profile("consola") // Solo se ejecuta si se activa el perfil "consola"
    public CommandLineRunner commandLineRunner(LibroRepository libroRepository, 
                                              AutorRepository autorRepository) {
        return args -> {
            Principal principal = new Principal(libroRepository, autorRepository);
            principal.muestraElmenu();
        };
    }

	
}
