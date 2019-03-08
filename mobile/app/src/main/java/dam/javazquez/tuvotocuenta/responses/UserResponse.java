package dam.javazquez.tuvotocuenta.responses;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

    private String _id;
    private String name;
    private String email;
    private String password;
    private String city;
    private String role;
    private String picture;
    private PartidoResponse partido;
    private List<PropuestaResponse> propuestas = new ArrayList<>();

    public UserResponse(){}

    public UserResponse(String name, String email, String password, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
    }

    public UserResponse(String _id, String name, String email, String password, String city, String role, String picture, PartidoResponse partido, List<PropuestaResponse> propuestas) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.role = role;
        this.picture = picture;
        this.partido = partido;
        this.propuestas = propuestas;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
