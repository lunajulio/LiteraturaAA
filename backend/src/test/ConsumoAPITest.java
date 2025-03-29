package com.alura.LiteraturA.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConsumoAPITest {

    @Autowired
    private ConsumoAPI consumoAPI;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerDatos_DebeRetornarJSON() {
        // Este test hace una llamada real a la API
        // En un entorno de producción, consideraría usar WireMock para simular la API
        String respuesta = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=pride");
        
        // Verificar que la respuesta no sea nula y contenga algún texto JSON
        assertNotNull(respuesta);
        assertTrue(respuesta.contains("results"));
    }
}
