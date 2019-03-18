package dam.javazquez.tuvotocuenta.retrofit.services;

import dam.javazquez.tuvotocuenta.responses.PartidoResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartidoService {
    final String BASE_URL = "/partidos";

    @GET(BASE_URL)
    Call<ResponseContainer<PartidoResponse>> listarPartidos();

    @GET(BASE_URL + "/{id}")
    Call<PartidoResponse> getOne(@Path("id") String id);
}
