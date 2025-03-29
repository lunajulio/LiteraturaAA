package com.alura.LiteraturA;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;
import com.alura.LiteraturA.service.AutorService;
import com.alura.LiteraturA.service.ConsumoAPI;
import com.alura.LiteraturA.service.ConvierteDatos;
import com.alura.LiteraturA.service.LibroService;

@SpringBootTest
class LiteraturAApplicationTests {

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private LibroService libroService;
    
    @Autowired
    private AutorService autorService;
    
    @Autowired
    private ConsumoAPI consumoAPI;
    
    @Autowired
    private ConvierteDatos convierteDatos;

    @Test
    void contextLoads() {
        // Verifica que todos los componentes principales se carguen correctamente
        assertNotNull(libroRepository);
        assertNotNull(autorRepository);
        assertNotNull(libroService);
        assertNotNull(autorService);
        assertNotNull(consumoAPI);
        assertNotNull(convierteDatos);
    }
}