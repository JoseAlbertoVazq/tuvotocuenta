package dam.javazquez.tuvotocuenta.responses;

public class PropuestaResponse {

    private String id;
    private String titulo;
    private String contenido;
    private PartidoResponse partido;
    private UserResponse creador;
    private MateriaResponse materia;

    public PropuestaResponse(){}

    public PropuestaResponse(String id, String titulo, String contenido, PartidoResponse partido, UserResponse creador, MateriaResponse materia) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.partido = partido;
        this.creador = creador;
        this.materia = materia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public PartidoResponse getPartido() {
        return partido;
    }

    public void setPartido(PartidoResponse partido) {
        this.partido = partido;
    }

    public UserResponse getCreador() {
        return creador;
    }

    public void setCreador(UserResponse creador) {
        this.creador = creador;
    }

    public MateriaResponse getMateria() {
        return materia;
    }

    public void setMateria(MateriaResponse materia) {
        this.materia = materia;
    }
}
