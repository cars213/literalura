package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer anoDeNacimiento;
    private Integer anoDeFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libros> libros;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anoDeNacimiento = datosAutor.anoDeNacimiento();
        this.anoDeFallecimiento = datosAutor.anoDeFallecimiento();
    }

    public Autor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public Integer getAnoDeNacimiento() {
        return anoDeNacimiento;
    }

    public void setAnoDeNacimiento(Integer anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public Integer getAnoDeFallecimiento() {
        return anoDeFallecimiento;
    }

    public void setAnoDeFallecimiento(Integer anoDeFallecimiento) {
        this.anoDeFallecimiento = anoDeFallecimiento;
    }


    @Override
    public String toString() {
        return "[ name = " + nombre +
                ", añoDeNacimiento = " + anoDeNacimiento +
                ", añoDeFallecimiento = " + anoDeFallecimiento + " ]";
    }
}
