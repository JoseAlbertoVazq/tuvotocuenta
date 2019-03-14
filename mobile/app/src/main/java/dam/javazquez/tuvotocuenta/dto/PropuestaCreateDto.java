package dam.javazquez.tuvotocuenta.dto;

import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;

public class PropuestaCreateDto {

    private String titulo;
    private String contenido;
    private String materia;
    private String partido;

    public PropuestaCreateDto() {

    }

    public PropuestaCreateDto(String titulo, String contenido, String materia, String partido) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.materia = materia;
        this.partido = partido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
}
