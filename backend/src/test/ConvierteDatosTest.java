package com.alura.LiteraturA.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alura.LiteraturA.model.Datos;
import com.alura.LiteraturA.model.DatosLibro;

@SpringBootTest
public class ConvierteDatosTest {

    @Autowired
    private ConvierteDatos convierteDatos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerDatos_DebeParsearJSONCorrectamente() {
        // Arrange
        String json = "{\"count\": 1, \"next\": null, \"previous\": null, \"results\": [{\"title\": \"Test Book\", \"download_count\": 100, \"authors\": [{\"name\": \"Test Author\"}], \"languages\": [\"en\"]}]}";
        
        // Act
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        
        // Assert
        assertNotNull(datos);
        assertNotNull(datos.resultados());
        assertEquals(1, datos.resultados().size());
        
        DatosLibro libro = datos.resultados().get(0);
        assertEquals("Test Book", libro.titulo());
        assertEquals(100.0, libro.descargas());
        assertNotNull(libro.autor());
        assertEquals(1, libro.autor().size());
        assertEquals("Test Author", libro.autor().get(0).nombre());
        assertNotNull(libro.idiomas());
        assertEquals(1, libro.idiomas().size());
        assertEquals("en", libro.idiomas().get(0));
    }

    @Test
    void obtenerDatos_ConJSONInvalido_DebeLanzarExcepcion() {
        // Arrange
        String jsonInvalido = "{invalid json}";
        
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            convierteDatos.obtenerDatos(jsonInvalido, Datos.class);
        });
        
        assertTrue(exception.getMessage().contains("Error al convertir"));
    }
}