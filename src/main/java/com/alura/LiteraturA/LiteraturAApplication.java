//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA;

import com.alura.LiteraturA.principal.Principal;
import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturAApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository librorepositorio;
	@Autowired
	private AutorRepository autorrepositorio;

	public LiteraturAApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturAApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Principal principal = new Principal(this.librorepositorio, this.autorrepositorio);
		principal.muestraElmenu();
	}
}
