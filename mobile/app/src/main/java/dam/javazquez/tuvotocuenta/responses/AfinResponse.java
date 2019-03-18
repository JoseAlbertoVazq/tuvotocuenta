package dam.javazquez.tuvotocuenta.responses;

public class AfinResponse {

    private String id;
    private Long partidoCount;

    public AfinResponse() {

    }

    public AfinResponse(String id, Long partidoCount) {
        this.id = id;
        this.partidoCount = partidoCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPartidoCount() {
        return partidoCount;
    }

    public void setPartidoCount(Long partidoCount) {
        this.partidoCount = partidoCount;
    }
}
