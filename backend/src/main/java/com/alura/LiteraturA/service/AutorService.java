package com.alura.LiteraturA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.repository.AutorRepository;


@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> obtenerAutoresVivosEnAño(int año) {
        return autorRepository.autoresVivosEnDeterminadoAño(año);
    }
    
}
