package com.aluracursos.literalura.Principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import java.util.*;


public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private final String URL_BUSQUEDA = "?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private ResultadoBusqueda resultadoBusqueda;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private boolean menu = true;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu(){
        System.out.println("""
                ***********************************************
                
                Bienvenido a literalura.
                
                ***********************************************
                """);


        while (menu){
            System.out.println("""
                    Por favor ingrese una opcion valida
                    ***********************************************
                    
                    0 - Cerrar aplicacion
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar Autores Registrados
                    4 - Listar Autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    ***********************************************
                    """);
            if(teclado.hasNextInt()) {
                var opcion = teclado.nextInt();
                teclado.nextLine();
                switch (opcion) {
                    case 1:
                        BuscarLibroPorTitulo();
                        break;
                    case 2:
                        ListarlibrosRegistrados();
                        break;
                    case 3:
                        ListarAutoressRegistrados();
                        break;
                    case 4:
                        System.out.println("Por favor ingrese el año que desea consultar");
                        listarAutoresVivosEnUnDeterminadoAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la libreria literalura");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            }else {
                System.out.println("Valor ingresado no es un numero por favor elija una opcion valida");
                teclado.next();
            }
        }
        teclado.close();
    }

    private void BuscarLibroPorTitulo(){
        System.out.println("Escriba el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+URL_BUSQUEDA+nombreLibro.replace(" ", "%20"));
        resultadoBusqueda = convierteDatos.obtenerDatos(json, ResultadoBusqueda.class);
        if(resultadoBusqueda.getDatosLibros() != null || !resultadoBusqueda.getDatosLibros().isEmpty())
        {
            Libros libro = new Libros(resultadoBusqueda.getDatosLibros().get(0));
            if(!libroRepository.findByTituloContainsIgnoreCase(libro.getTitulo()).isPresent()){
                libroRepository.save(libro);
            }
            System.out.println("El libro buscado es: " + libro);
        }else {
            System.out.println("Lo siento, ese libro no se encuentra disponible");
        }
    }

    private void ListarlibrosRegistrados(){
        List<Libros> libros = libroRepository.findAll();
        System.out.println("""
                ***********************************************
                Estos son los libros de nuestro portafolio
                """);
        libros.stream().sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);
        System.out.println("***********************************************");
    }

    private void ListarAutoressRegistrados(){
        List<Autor> autores = autorRepository.findAll();
        System.out.println("""
                ***********************************************
                Estos son los autores de nuestro portafolio
                """);
        autores.stream().sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
        System.out.println("***********************************************");
    }

    private void listarAutoresVivosEnUnDeterminadoAnio(){
        boolean validacionNumero = true;
        while (validacionNumero){
            if(teclado.hasNextInt()){
                int anioDigitado = teclado.nextInt();
                validacionNumero = false;
                List<Autor> autores = autorRepository.findByAnoDeNacimientoLessThanEqualAndAnoDeFallecimientoGreaterThanEqual(anioDigitado,anioDigitado);
                if(!autores.isEmpty()){
                    System.out.println("***********************************************");
                    System.out.println("Estos son los autores vivos desde " + anioDigitado + " encontrados en nuestro portafolio:");
                    autores.forEach(System.out::println);
                    System.out.println("***********************************************");
                }else {
                    System.out.println("Disculpa, no hay autores registrado en la fecha indicada");
                }

            }else {
                System.out.println("Por favor digite un año valido");
                teclado.next();
            }
        }
        teclado.nextLine();
    }


    private void listarLibrosPorIdioma(){
        System.out.println("""
                ***********************************************
                Idiomas disponibles para buscar:
                es - Español
                en - Ingles
                Por favor escriba la forma corta del idioma indicada anteriomente
                ***********************************************
                """);
        boolean validacionidioma = true;
        while (validacionidioma){
            if (teclado.hasNextLine()){
                var idioma = teclado.nextLine();
                List<Libros> libros = libroRepository.findByIdiomas(idioma);
                if (!libros.isEmpty()){
                    System.out.println("***********************************************");
                    System.out.println("Estos son los libros del idioma ( " + idioma + " ) encontrados en nuestro portafolio:");
                    libros.forEach(System.out::println);
                    System.out.println("***********************************************");
                }else {
                    System.out.println("Disculpa no se encontraron libros en el lenguaje ( " + idioma + " ) en nuestro portafolio");
                }
                validacionidioma = false;
            }else {
                System.out.println("Ha ocurrido un error por favor digite un idioma valido");
            }
        }
    }

}
