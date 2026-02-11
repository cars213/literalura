package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoBusqueda {
    @JsonAlias("results") List<DatosLibros> datosLibros;

    public List<DatosLibros> getDatosLibros() {
        return datosLibros;
    }
}
