package dam.javazquez.tuvotocuenta.dto;

import dam.javazquez.tuvotocuenta.responses.PartidoResponse;

public class UserEditedDto {

    private String name;
    private String ciudad;
    private String email;
    private String picture;
    private String partido;


    public UserEditedDto(){

    }

    public UserEditedDto(String email, String partido) {
        this.email = email;
        this.partido = partido;
    }

    public UserEditedDto(String name, String picture, String email, String ciudad, String partido) {
        this.name = name;
        this.ciudad = ciudad;
        this.email = email;
        this.picture = picture;
        this.partido = partido;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
}
