
package com.alura.LiteraturA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.model.DatosLibro;
import com.alura.LiteraturA.model.Libro;
import com.alura.LiteraturA.service.AutorService;
import com.alura.LiteraturA.service.LibroService;

@RestController
@RequestMapping("/api")
public class BibliotecaController {
	
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private AutorService autorService;

    // Buscar libro por nombre en la API
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarLibrosEnAPI(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscarLibroPorNombre(titulo));
    }

    // Listar todos los libros
    @GetMapping("/libros")
    public List<Libro> listarLibros() {
        return libroService.obtenerTodosLosLibros();
    }

    
    // Buscar libro por ID   
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroService.obtenerLibroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar un libro
    @PostMapping("/libros")
    public ResponseEntity<Libro> guardarLibro(@RequestBody DatosLibro datosLibro) {
        Libro libroGuardado = libroService.guardarLibro(datosLibro);
        return ResponseEntity.ok(libroGuardado);
    }

    // Listar todos los autores
    @GetMapping("/autores")
    public List<Autor> listarAutores() {
        return autorService.obtenerTodosLosAutores();
    }

    // Filtrar libros por idioma
    @GetMapping("/libros/idioma/{idioma}")
    public List<Libro> filtrarLibrosPorIdioma(@PathVariable String idioma) {
        return libroService.filtrarPorIdioma(idioma);
    }
    
    // Buscar autores vivos en un año específico
    @GetMapping("/autores/vivos/{año}")
    public List<Autor> autoresVivosEnAño(@PathVariable int año) {
        return autorService.obtenerAutoresVivosEnAño(año);
    }

    
    
}
