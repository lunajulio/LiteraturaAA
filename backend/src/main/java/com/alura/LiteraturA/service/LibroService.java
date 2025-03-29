
package com.alura.LiteraturA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.model.Datos;
import com.alura.LiteraturA.model.DatosAutor;
import com.alura.LiteraturA.model.DatosLibro;
import com.alura.LiteraturA.model.Libro;
import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;

@Service
public class LibroService {

    private static final String URL_BASE = "https://gutendex.com/books/";
    
    @Autowired
    private ConsumoAPI consumoAPI;
    
    @Autowired
    private ConvierteDatos conversor;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;

    public List<DatosLibro> buscarLibroPorNombre(String nombreLibro) {
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "+");
        String json = consumoAPI.obtenerDatos(url);
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        return datos.resultados();
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);
    }

    public Libro guardarLibro(DatosLibro datosLibro) {
        // Buscar o crear el autor
        DatosAutor datosAutor = datosLibro.autor().get(0);  // Tomamos el primer autor
        String nombreAutor = datosAutor.nombre();
        
        Autor autor = autorRepository.findByNombre(nombreAutor)
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(datosAutor);
                    return autorRepository.save(nuevoAutor);
                });
        
        // Crear y preparar el libro
        Libro libro = new Libro(datosLibro);
        libro.setAutor(autor);
        
        if (datosLibro.formats() != null && datosLibro.formats().containsKey("image/jpeg")) {
            libro.setUrlPortada(datosLibro.formats().get("image/jpeg"));
        }
        
        return libroRepository.save(libro);
    }

    public List<Libro> filtrarPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }



    
}
