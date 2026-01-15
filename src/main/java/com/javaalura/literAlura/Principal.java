package com.javaalura.literAlura;

import com.javaalura.literAlura.dto.AutorDTO;
import com.javaalura.literAlura.dto.LibroDTO;
import com.javaalura.literAlura.model.Autor;
import com.javaalura.literAlura.model.Datos;
import com.javaalura.literAlura.model.DatosLibros;
import com.javaalura.literAlura.model.Libro;
import com.javaalura.literAlura.services.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {
    private final ConsumeAPI consumeApi;
    private final ConvierteDatos convierteDatos;
    private final ServiceLibro serviceLibro;
    private final ServiceAutor serviceAutor;
    private final MostrarLibrosAndAutores mostrarLibrosAndAutores;
    private final Validaciones validaciones;
    private final Scanner entrada;
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String SERCH = "?search=";
    private int opcion = 1;

    // Inyección por constructor
    public Principal(ConsumeAPI consumeApi, ConvierteDatos convierteDatos,
                     ServiceLibro serviceLibro, MostrarLibrosAndAutores mostrarLibrosAndAutores,
                     ServiceAutor serviceAutor, Validaciones validaciones) {
        this.consumeApi = consumeApi;
        this.convierteDatos = convierteDatos;
        this.serviceLibro = serviceLibro;
        this.entrada = new Scanner(System.in);
        this.mostrarLibrosAndAutores = mostrarLibrosAndAutores;
        this.serviceAutor= serviceAutor;
        this.validaciones=validaciones;
    }

    public void menu() {
        while (opcion != 0) {
            System.out.println("Elija la opcion a traves de su numero: ");
            System.out.println("1- buscar libro por titulo");
            System.out.println("2- listar libros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos en un determinado año");
            System.out.println("5- listar libros por idiomas");
            System.out.println("0- salir");
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del libro que desea buscar: ");
                    var libroBuscado = entrada.nextLine();
                    Optional<Libro> libro = validaciones.verificarSiElLibroEstaEnLaBaseDedatos(libroBuscado);
                    if (libro.isEmpty()) {
                        var busqueda = consumeApi
                                .obtenerDatos(URL_BASE + SERCH + libroBuscado.replace(" ", "+"));
                        var datosDeBusqueda = convierteDatos.convertdata(busqueda, Datos.class);
                        Optional<DatosLibros> librosOptional = datosDeBusqueda.libros().stream()
                                .filter(l -> l.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                                .findFirst();

                        if (librosOptional.isPresent()) {
                            Autor autor = validaciones
                                    .verificarSiExisteAutorEnLaBaseDeDatos(librosOptional
                                            .get().autor().get(0).nombre());
                            if (autor != null) {
                                System.out.println("El autor ya esta en la base de datos!");
                                serviceLibro.crearLibroEnDBConAutorEncontrado(librosOptional.get(), autor);
                            } else {
                                System.out.println("Libro encontrado: ");
                                serviceLibro.crearLibroEnBaseDeDatos(librosOptional.get());
                            }
                        } else {
                            System.out.println("El titulo buscado no se encontro!!!");
                        }
                    }else{
                        System.out.println("El libro ya existe en la base de datos!");
                    }
                    break;
                case 2:
                    System.out.println("***********Libros en Base de Datos**************\n");
                    List<LibroDTO> listaDeLibro= serviceLibro.listaDeLibros();
                    mostrarLibrosAndAutores.mostrarListaDeLibros(listaDeLibro);
                    break;
                case 3:
                    System.out.println("***********Autores en Base de Datos**************\n");
                    List<AutorDTO> autorDTOList = serviceAutor.listaDeAutores();
                    mostrarLibrosAndAutores.mostrarListaDeAutores(autorDTOList);
                    break;
                case 4:
                    System.out.println("Ingrese el año para ver los autores vivos en dicho año: ");
                    var anio= entrada.nextLine();
                    Integer anioInt= Integer.parseInt(anio);
                    List<AutorDTO> autoresPorVivosPorAnio = serviceAutor
                            .getAutoresPorVivosPorAnio(anioInt);
                    System.out.println("listar autores vivos en un determinado año");
                    if (!autoresPorVivosPorAnio.isEmpty()){
                        mostrarLibrosAndAutores.mostrarListaDeAutores(autoresPorVivosPorAnio);
                    }else {
                        System.out.println("No se encontró ninguna coincidencia ❌");
                    }
                    break;
                case 5:
                    System.out.println("listar libros por idiomas: ");
                    System.out.println("""
                            Ingrese el idioma para buscar los libros:
                            es- español
                            en- inglés
                            fr- francés
                            pt- portugués
                            """);
                    var idioma= entrada.nextLine();
                    List<LibroDTO> listaDeLibrosPorIdioma = serviceLibro
                            .getListaDeLibrosPorIdioma(idioma);
                    if (listaDeLibrosPorIdioma.isEmpty()){
                        System.out.println("No hay libros en ese idioma por el momento en la base de datos!");
                    }else{
                        mostrarLibrosAndAutores.mostrarListaDeLibros(listaDeLibrosPorIdioma);
                    }
                    break;
                case 0:
                    System.out.println("Gracias por usar nuestro servicio 👋");
                    break;
                default:
                    System.out.println("opcion no valida");
                    break;
            }


        }
    }

}
