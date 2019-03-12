export class PartidoCreateDto {
    nombre: String;
    siglas: String;
    picture: String;

    constructor(nombre: String, siglas: String, picture: String) {
        this.nombre = nombre;
        this.siglas = siglas;
        this.picture = picture;
    }
}
