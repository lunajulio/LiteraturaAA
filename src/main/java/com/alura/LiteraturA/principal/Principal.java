//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alura.LiteraturA.principal;

import com.alura.LiteraturA.model.Autor;
import com.alura.LiteraturA.model.Datos;
import com.alura.LiteraturA.model.DatosAutor;
import com.alura.LiteraturA.model.DatosLibro;
import com.alura.LiteraturA.model.Libro;
import com.alura.LiteraturA.repository.AutorRepository;
import com.alura.LiteraturA.repository.LibroRepository;
import com.alura.LiteraturA.service.ConsumoAPI;
import com.alura.LiteraturA.service.ConvierteDatos;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado;
    private LibroRepository Librorepositorio;
    private AutorRepository Autorrepositorio;
    private List<Libro> libros;
    private List<Autor> autor;

    public Principal() {
        this.teclado = new Scanner(System.in);
    }

    public Principal(LibroRepository librorepositorio, AutorRepository autorrepositorio) {
        this.teclado = new Scanner(System.in);
        this.Librorepositorio = librorepositorio;
        this.Autorrepositorio = autorrepositorio;
    }

    public void muestraElmenu() {
        int opcion = -1;

        while(opcion != 0) {
            String menu = "1 - Buscar libro\n2 - Consultar libros\n3 - Consultar autores\n4 - Listando libros por idioma\n5 - Listando autores vivos en determinado año\n0 - Salir\n";
            System.out.println(menu);
            opcion = this.teclado.nextInt();
            this.teclado.nextLine();
            switch (opcion) {
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                case 1:
                    this.BusquedaLibrosPorNombre();
                    break;
                case 2:
                    this.mostrarLibrosBuscados();
                    break;
                case 3:
                    this.mostrarAutores();
                    break;
                case 4:
                    System.out.println("En qué idioma quieres ver los libros?\n1. Español \n2. Inglés \n3. Francés \n4. Italiano \n");
                    int op = this.teclado.nextInt();
                    String idioma = "";
                    switch (op) {
                        case 1 -> idioma = "es";
                        case 2 -> idioma = "en";
                        case 3 -> idioma = "fr";
                        case 4 -> idioma = "it";
                        default -> System.out.println("Idioma inválido");
                    }

                    this.listarLibrosPorIdioma(idioma);
                    break;
                case 5:
                    this.listarAutoresVivosEnDeterminadoAño();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private Datos getDatosLibro() {
        System.out.println("Escribe el nombre del Libro que deseas buscar");
        String nombreLibro = this.teclado.nextLine();
        String json = this.consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + nombreLibro.replace(" ", "+"));
        System.out.println(json);
        Datos datos = (Datos)this.conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);
        return datos;
    }

    private void leerLibro(Libro libro) {
        System.out.printf("----- LIBRO -----\nTitulo: %s\nAutor: %s\nIdioma: %s\nNumero de descargas: %.0f\n-------------------- \n\n\n", libro.getTitulo(), libro.getAutor().getNombre(), libro.getIdioma(), libro.getDescargas());
    }

    private void BusquedaLibrosPorNombre() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String nombreLibro = this.teclado.nextLine();
        String json = this.consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + nombreLibro.replace(" ", "+"));
        Datos datos = (Datos)this.conversor.obtenerDatos(json, Datos.class);
        List<DatosLibro> libros = datos.resultados();
        Optional<DatosLibro> libroOptional = libros.stream().filter((l) -> {
            return l.titulo().toLowerCase().contains(nombreLibro.toLowerCase());
        }).findFirst();
        if (libroOptional.isPresent()) {
            DatosLibro datosLibro = (DatosLibro)libroOptional.get();
            Iterator var7 = datosLibro.autor().iterator();

            while(var7.hasNext()) {
                DatosAutor datosAutor = (DatosAutor)var7.next();
                String nombreAutor = datosAutor.nombre();
                Autor autor = (Autor)this.Autorrepositorio.findByNombre(nombreAutor).orElseGet(() -> {
                    Autor nuevoAutor = new Autor(datosAutor);
                    return (Autor)this.Autorrepositorio.save(nuevoAutor);
                });
                Libro libro = new Libro(datosLibro);
                libro.setAutor(autor);

                try {
                    this.Librorepositorio.save(libro);
                    this.leerLibro(libro);
                } catch (Exception var13) {
                    System.out.println("\n El libro ya existe en la base de datos.");
                }
            }
        } else {
            System.out.println("\n El libro no ha podido ser encontrado");
        }

    }

    private void mostrarLibrosBuscados() {
        this.libros = this.Librorepositorio.findAll();
        Iterator var1 = this.libros.iterator();

        while(var1.hasNext()) {
            Libro libro = (Libro)var1.next();
            this.leerLibro(libro);
        }

    }

    private void mostrarAutores() {
        this.autor = this.Autorrepositorio.findAll();
        Iterator var1 = this.autor.iterator();

        while(var1.hasNext()) {
            Autor autor = (Autor)var1.next();
            System.out.printf("----- AUTOR -----\nNombre: %s\nFecha de Nacimiento: %s\nFecha de Fallecimiento: %s\n-------------------- \n\n", autor.getNombre(), autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento() : "N/A", autor.getFechaDeFallecimiento() != null ? autor.getFechaDeFallecimiento() : "N/A");
        }

    }

    private void listarAutoresVivosEnDeterminadoAño() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        int año = this.teclado.nextInt();
        this.teclado.nextLine();
        List<Autor> autoresVivos = this.Autorrepositorio.autoresVivosEnDeterminadoAño(año);
        Iterator var3 = autoresVivos.iterator();

        while(var3.hasNext()) {
            Autor autor = (Autor)var3.next();
            System.out.printf("----- AUTOR -----\nNombre: %s\nFecha de Nacimiento: %s\nFecha de Fallecimiento: %s\n-------------------- \n\n", autor.getNombre(), autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento() : "N/A", autor.getFechaDeFallecimiento() != null ? autor.getFechaDeFallecimiento() : "N/A");
        }

    }

    private void listarLibrosPorIdioma(String idioma) {
        try {
            this.libros = this.Librorepositorio.findByIdioma(idioma);
            if (this.libros == null) {
                System.out.println("No hay Libros registrados");
            } else {
                Iterator var2 = this.libros.iterator();

                while(var2.hasNext()) {
                    Libro libro = (Libro)var2.next();
                    this.leerLibro(libro);
                }
            }
        } catch (Exception var4) {
            System.out.println("Error en la busqueda");
        }

    }
}
