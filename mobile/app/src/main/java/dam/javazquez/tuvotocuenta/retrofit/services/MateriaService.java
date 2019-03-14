package dam.javazquez.tuvotocuenta.retrofit.services;

import dam.javazquez.tuvotocuenta.responses.MateriaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MateriaService {

    final String BASE_URL = "/materias";

    @GET(BASE_URL)
    Call<ResponseContainer<MateriaResponse>> listarMaterias();
}
