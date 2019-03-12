package dam.javazquez.tuvotocuenta.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PartidoResponse implements Serializable {

    private String id;
    private String nombre;
    private String siglas;
    private String picture;
    private List<PropuestaResponse> propuestas = new ArrayList<>();

    public PartidoResponse(){}

    public PartidoResponse(String id, String nombre, String siglas, List<PropuestaResponse> propuestas, String picture) {
        this.id = id;
        this.nombre = nombre;
        this.siglas = siglas;
        this.propuestas = propuestas;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public List<PropuestaResponse> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaResponse> propuestas) {
        this.propuestas = propuestas;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
