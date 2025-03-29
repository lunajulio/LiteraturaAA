package com.alura.LiteraturA.controller;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.model.DatosLibro;
import com.alura.LiteraturA.model.Libro;
import com.alura.LiteraturA.service.AutorService;
import com.alura.LiteraturA.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BibliotecaController.class)
public class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @MockBean
    private AutorService autorService;

    private Libro libro1;
    private Libro libro2;
    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setId(1L);
        autor.setNombre("Miguel de Cervantes");
        autor.setFechaDeNacimiento("1547");
        autor.setFechaDeFallecimiento("1616");

        libro1 = new Libro();
        libro1.setId(1L);
        libro1.setTitulo("Don Quijote");
        libro1.setIdioma("es");
        libro1.setAutor(autor);
        libro1.setDescargas(1000.0);
        libro1.setUrlPortada("https://www.ejemplo.com/portada1.jpg");

        libro2 = new Libro();
        libro2.setId(2L);
        libro2.setTitulo("La Galatea");
        libro2.setIdioma("es");
        libro2.setAutor(autor);
        libro2.setDescargas(500.0);
        libro2.setUrlPortada("https://www.ejemplo.com/portada2.jpg");
    }

    @Test
    void listarLibros_DebeRetornarListaDeLibros() throws Exception {
        List<Libro> libros = Arrays.asList(libro1, libro2);
        when(libroService.obtenerTodosLosLibros()).thenReturn(libros);

        mockMvc.perform(get("/api/libros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo").value("Don Quijote"))
                .andExpect(jsonPath("$[1].titulo").value("La Galatea"));
    }

    @Test
    void obtenerLibroPorId_CuandoExisteLibro_DebeRetornarLibro() throws Exception {
        when(libroService.obtenerLibroPorId(1L)).thenReturn(Optional.of(libro1));

        mockMvc.perform(get("/api/libros/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Don Quijote"))
                .andExpect(jsonPath("$.idioma").value("es"));
    }

    @Test
    void obtenerLibroPorId_CuandoNoExisteLibro_DebeRetornar404() throws Exception {
        when(libroService.obtenerLibroPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void filtrarLibrosPorIdioma_DebeRetornarLibrosFiltrados() throws Exception {
        List<Libro> libros = Arrays.asList(libro1, libro2);
        when(libroService.filtrarPorIdioma("es")).thenReturn(libros);

        mockMvc.perform(get("/api/libros/idioma/es")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idioma").value("es"))
                .andExpect(jsonPath("$[1].idioma").value("es"));
    }

    @Test
    void buscarLibrosEnAPI_DebeRetornarLibrosEncontrados() throws Exception {
        List<DatosLibro> datosLibros = new ArrayList<>();
        // Aquí deberías crear instancias de DatosLibro para simular una respuesta
        
        when(libroService.buscarLibroPorNombre(anyString())).thenReturn(datosLibros);

        mockMvc.perform(get("/api/buscar?titulo=quijote")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void listarAutores_DebeRetornarListaDeAutores() throws Exception {
        List<Autor> autores = List.of(autor);
        when(autorService.obtenerTodosLosAutores()).thenReturn(autores);

        mockMvc.perform(get("/api/autores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Miguel de Cervantes"));
    }

    @Test
    void autoresVivosEnAño_DebeRetornarAutoresFiltrados() throws Exception {
        List<Autor> autores = List.of(autor);
        when(autorService.obtenerAutoresVivosEnAño(1600)).thenReturn(autores);

        mockMvc.perform(get("/api/autores/vivos/1600")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Miguel de Cervantes"));
    }
}
