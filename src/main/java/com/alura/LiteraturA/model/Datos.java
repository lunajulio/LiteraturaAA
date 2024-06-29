//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record Datos(List<DatosLibro> resultados) {
    public Datos(@JsonAlias({"results"}) List<DatosLibro> resultados) {
        this.resultados = resultados;
    }

    @JsonAlias({"results"})
    public List<DatosLibro> resultados() {
        return this.resultados;
    }
}
