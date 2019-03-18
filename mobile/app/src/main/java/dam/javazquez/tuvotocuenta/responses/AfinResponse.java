package dam.javazquez.tuvotocuenta.responses;

public class AfinResponse {

    private String _id;
    private Long partidoCount;

    public AfinResponse() {

    }

    public AfinResponse(String id, Long partidoCount) {
        this._id = id;
        this.partidoCount = partidoCount;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Long getPartidoCount() {
        return partidoCount;
    }

    public void setPartidoCount(Long partidoCount) {
        this.partidoCount = partidoCount;
    }
}
