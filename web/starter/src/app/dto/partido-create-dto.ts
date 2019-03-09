export class PartidoCreateDto {
    nombre: String;
    siglas: String;

    constructor(nombre: String, siglas: String) {
        this.nombre = nombre;
        this.siglas = siglas;
    }
}
