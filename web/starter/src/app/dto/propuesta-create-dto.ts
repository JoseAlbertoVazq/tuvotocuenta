export class PropuestaCreateDto {
    titulo: String;
    contenido: string;
    creador: String;
    partido: String;
    materia: String;

    constructor(    titulo: String,
        contenido: string,
        creador: String, partido: String, materia: String) {
            this.titulo = titulo;
            this.contenido = contenido;
            this.creador = creador;
            this.partido = partido;
            this.materia = materia;
        }
}
