
package com.alura.LiteraturA.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "autor"
)
public class Autor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    @Column(
            unique = true
    )
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;
    @OneToMany(
            mappedBy = "autor",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    private Set<Libro> libros = new HashSet();

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    public Autor() {
    }

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        String var10000 = this.nombre;
        return "AUTOR: " + var10000 + "\nFecha de Nacimiento: " + this.fechaDeNacimiento + "\nFecha de Defuncion: " + this.fechaDeFallecimiento + "\nLibros: " + (this.libros != null ? (String)this.libros.stream().map(Libro::getTitulo).collect(Collectors.joining(", ")) : "N/A") + "\n\n";
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return this.fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }
}
