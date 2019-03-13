package dam.javazquez.tuvotocuenta.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserResponse implements Serializable {

    private String id;
    private String name;
    private String email;
    private String password;
    private String ciudad;
    private String role;
    private String picture;
    private PartidoResponse partido;
    private List<PropuestaResponse> propuestas = new ArrayList<>();

    public UserResponse(){}

    public UserResponse(String name, String email, String password, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ciudad = city;
    }

    public UserResponse(String _id, String name, String email, String password, String city, String role, String picture, PartidoResponse partido, List<PropuestaResponse> propuestas) {
        this.id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.ciudad = city;
        this.role = role;
        this.picture = picture;
        this.partido = partido;
        this.propuestas = propuestas;
    }

    public String get_id() {
        return id;
    }

    public void set_id(String _id) {
        this.id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public PartidoResponse getPartido() {
        return partido;
    }

    public void setPartido(PartidoResponse partido) {
        this.partido = partido;
    }

    public List<PropuestaResponse> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaResponse> propuestas) {
        this.propuestas = propuestas;
    }
}
