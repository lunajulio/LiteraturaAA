package com.alura.LiteraturA.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.repository.AutorRepository;

public class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosAutores_DebeRetornarListaDeAutores() {
        // Arrange
        List<Autor> autoresEsperados = new ArrayList<>();
        Autor autor1 = new Autor();
        autor1.setNombre("Miguel de Cervantes");
        Autor autor2 = new Autor();
        autor2.setNombre("Federico García Lorca");
        autoresEsperados.add(autor1);
        autoresEsperados.add(autor2);
        
        when(autorRepository.findAll()).thenReturn(autoresEsperados);

        // Act
        List<Autor> resultado = autorService.obtenerTodosLosAutores();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Miguel de Cervantes", resultado.get(0).getNombre());
        assertEquals("Federico García Lorca", resultado.get(1).getNombre());
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void obtenerAutoresVivosEnAño_DebeRetornarAutoresFiltrados() {
        // Arrange
        List<Autor> autoresEsperados = new ArrayList<>();
        Autor autor = new Autor();
        autor.setNombre("Miguel de Cervantes");
        autor.setFechaDeNacimiento("1547");
        autor.setFechaDeFallecimiento("1616");
        autoresEsperados.add(autor);
        
        when(autorRepository.autoresVivosEnDeterminadoAño(1600)).thenReturn(autoresEsperados);

        // Act
        List<Autor> resultado = autorService.obtenerAutoresVivosEnAño(1600);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Miguel de Cervantes", resultado.get(0).getNombre());
        verify(autorRepository, times(1)).autoresVivosEnDeterminadoAño(1600);
    }
}