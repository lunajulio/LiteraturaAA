//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA.model;

import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "libro"
)
public class Libro {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    @Column(
            unique = true
    )
    private String titulo;
    private String idioma;
    @ManyToOne
    @JoinColumn(
            name = "autor_id"
    )
    private Autor autor;
    private Double descargas;

    @Column(length=1000)
    private String urlPortada;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        Optional<DatosAutor> autor = datosLibro.autor().stream().findFirst();
        if (autor.isPresent()) {
            this.autor = new Autor((DatosAutor)autor.get());
        } else {
            System.out.println("No se ha podido encontrar el autor");
        }

        this.idioma = (String)datosLibro.idiomas().get(0);
        this.descargas = datosLibro.descargas();
        this.urlPortada = datosLibro.formats().get("image/jpeg");
    }

    public String toString() {
        String var10000 = this.titulo;
        return "Titulo: " + var10000 + "\nAutor: " + String.valueOf(this.autor) + "\nIdiomas: " + this.idioma + "\nDescargas: " + this.descargas + "\n";
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return this.idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Double getDescargas() {
        return this.descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public String getUrlPortada() {
        return urlPortada;
    }
    
    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }
}
