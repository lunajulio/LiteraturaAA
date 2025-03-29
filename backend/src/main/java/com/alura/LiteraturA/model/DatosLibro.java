//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DatosLibro(String titulo, List<DatosAutor> autor, List<String> idiomas, Double descargas, Map<String, String> formats) {
    public DatosLibro(@JsonAlias({"title"}) String titulo, @JsonAlias({"authors"}) List<DatosAutor> autor, @JsonAlias({"languages"}) List<String> idiomas, @JsonAlias({"download_count"}) Double descargas,
            @JsonAlias({"formats"}) Map<String, String> formats) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.descargas = descargas;
        this.formats = formats;
    }

    @JsonAlias({"title"})
    public String titulo() {
        return this.titulo;
    }

    @JsonAlias({"authors"})
    public List<DatosAutor> autor() {
        return this.autor;
    }

    @JsonAlias({"languages"})
    public List<String> idiomas() {
        return this.idiomas;
    }

    @JsonAlias({"download_count"})
    public Double descargas() {
        return this.descargas;
    }

    @JsonAlias({"formats"})
    public Map<String, String> formats() {
        return this.formats != null ? this.formats : Map.of();
    }
}
