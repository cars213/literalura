package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreAutor;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Integer numeroDeDescargas;
    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;


    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        if(datosLibros.autor() != null  && !datosLibros.autor().isEmpty()){
            this.autor = new Autor(datosLibros.autor().get(0));
        }
        this.nombreAutor = autor.getNombre();
        this.idiomas = datosLibros.lenguajes().get(0);
        this.numeroDeDescargas = datosLibros.descargas();
    }

    public Libros(){}

    @Override
    public String toString() {
        return "[ titulo = " + titulo +
                ", [ autor = " + autor +
                "] , idiomas = " + idiomas +
                ", numeroDeDescargas = " + numeroDeDescargas + " ]";
    }


    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }
}
