package dam.javazquez.tuvotocuenta.retrofit.services;

import dam.javazquez.tuvotocuenta.dto.PropuestaCreateDto;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropuestaService {

    String BASE_URL = "/propuestas";

    @GET(BASE_URL)
    Call<ResponseContainer<PropuestaResponse>> listaPropuestas();

    @GET(BASE_URL+"{id}")
    Call<PropuestaResponse> getOnePropuesta(@Path("id") String id);

    @POST(BASE_URL)
    Call<PropuestaCreateDto> createPropuesta();


}
