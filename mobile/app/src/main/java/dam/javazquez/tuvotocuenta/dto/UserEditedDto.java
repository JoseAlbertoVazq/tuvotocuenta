package dam.javazquez.tuvotocuenta.dto;

import dam.javazquez.tuvotocuenta.responses.PartidoResponse;

public class UserEditedDto {

    private String name;
    private String ciudad;
    private String email;
    private String picture;


    public UserEditedDto(){

    }

    public UserEditedDto(String name, String ciudad, String email, String picture) {
        this.name = name;
        this.ciudad = ciudad;
        this.email = email;
        this.picture = picture;
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
}
