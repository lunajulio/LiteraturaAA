package com.alura.LiteraturA.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.model.Datos;
import com.alura.LiteraturA.model.DatosAutor;
import com.alura.LiteraturA.model.DatosLibro;
import com.alura.LiteraturA.model.Libro;
import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;

public class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ConsumoAPI consumoAPI;

    @Mock
    private ConvierteDatos conversor;

    @InjectMocks
    private LibroService libroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosLibros_DebeRetornarListaDeLibros() {
        // Arrange
        List<Libro> librosEsperados = new ArrayList<>();
        Libro libro1 = new Libro();
        libro1.setTitulo("Don Quijote");
        Libro libro2 = new Libro();
        libro2.setTitulo("La Celestina");
        librosEsperados.add(libro1);
        librosEsperados.add(libro2);
        
        when(libroRepository.findAll()).thenReturn(librosEsperados);

        // Act
        List<Libro> resultado = libroService.obtenerTodosLosLibros();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Don Quijote", resultado.get(0).getTitulo());
        assertEquals("La Celestina", resultado.get(1).getTitulo());
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void obtenerLibroPorId_CuandoExisteLibro_DebeRetornarLibro() {
        // Arrange
        Libro libroEsperado = new Libro();
        libroEsperado.setId(1L);
        libroEsperado.setTitulo("Don Quijote");
        
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libroEsperado));

        // Act
        Optional<Libro> resultado = libroService.obtenerLibroPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Don Quijote", resultado.get().getTitulo());
        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerLibroPorId_CuandoNoExisteLibro_DebeRetornarVacio() {
        // Arrange
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Libro> resultado = libroService.obtenerLibroPorId(99L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(libroRepository, times(1)).findById(99L);
    }

    @Test
    void filtrarPorIdioma_DebeRetornarLibrosFiltrados() {
        // Arrange
        List<Libro> librosEsperados = new ArrayList<>();
        Libro libro = new Libro();
        libro.setIdioma("es");
        libro.setTitulo("Don Quijote");
        librosEsperados.add(libro);
        
        when(libroRepository.findByIdioma("es")).thenReturn(librosEsperados);

        // Act
        List<Libro> resultado = libroService.filtrarPorIdioma("es");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("es", resultado.get(0).getIdioma());
        assertEquals("Don Quijote", resultado.get(0).getTitulo());
        verify(libroRepository, times(1)).findByIdioma("es");
    }

    @Test
    void buscarLibroPorNombre_DebeRetornarResultadosDeLaAPI() {
        // Arrange
        String nombreLibro = "Quijote";
        String json = "{\"count\":1,\"next\":null,\"previous\":null,\"results\":[{\"title\":\"Don Quijote\"}]}";
        
        DatosLibro datosLibro = new DatosLibro("Don Quijote", new ArrayList<>(), new ArrayList<>(), 100.0, new HashMap<>());
        List<DatosLibro> listaResultados = new ArrayList<>();
        listaResultados.add(datosLibro);
        Datos datos = new Datos(listaResultados);
        
        when(consumoAPI.obtenerDatos(contains(nombreLibro))).thenReturn(json);
        when(conversor.obtenerDatos(json, Datos.class)).thenReturn(datos);

        // Act
        List<DatosLibro> resultado = libroService.buscarLibroPorNombre(nombreLibro);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Don Quijote", resultado.get(0).titulo());
    }

    @Test
    void guardarLibro_DebeGuardarLibroYRetornarlo() {
        // Arrange
        List<DatosAutor> autores = new ArrayList<>();
        DatosAutor datosAutor = new DatosAutor("Miguel de Cervantes", "1547", "1616");
        autores.add(datosAutor);
        
        Map<String, String> formats = new HashMap<>();
        formats.put("image/jpeg", "http://ejemplo.com/portada.jpg");
        
        DatosLibro datosLibro = new DatosLibro("Don Quijote", autores, List.of("es"), 100.0, formats);
        
        Autor autor = new Autor();
        autor.setNombre("Miguel de Cervantes");
        autor.setFechaDeNacimiento("1547");
        autor.setFechaDeFallecimiento("1616");
        
        Libro libroGuardado = new Libro();
        libroGuardado.setId(1L);
        libroGuardado.setTitulo("Don Quijote");
        libroGuardado.setAutor(autor);
        libroGuardado.setIdioma("es");
        libroGuardado.setDescargas(100.0);
        libroGuardado.setUrlPortada("http://ejemplo.com/portada.jpg");
        
        when(autorRepository.findByNombre("Miguel de Cervantes")).thenReturn(Optional.of(autor));
        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);

        // Act
        Libro resultado = libroService.guardarLibro(datosLibro);

        // Assert
        assertEquals("Don Quijote", resultado.getTitulo());
        assertEquals("Miguel de Cervantes", resultado.getAutor().getNombre());
        assertEquals("http://ejemplo.com/portada.jpg", resultado.getUrlPortada());
        verify(libroRepository, times(1)).save(any(Libro.class));
    }
}