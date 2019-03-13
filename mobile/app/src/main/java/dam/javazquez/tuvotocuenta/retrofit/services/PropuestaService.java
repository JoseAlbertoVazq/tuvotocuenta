package dam.javazquez.tuvotocuenta.retrofit.services;

import dam.javazquez.tuvotocuenta.dto.PropuestaCreateDto;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PropuestaService {

    String BASE_URL = "/propuestas";

    @GET(BASE_URL)
    Call<ResponseContainer<PropuestaResponse>> listaPropuestas();

    @GET(BASE_URL+"/{id}")
    Call<PropuestaResponse> getOnePropuesta(@Path("id") String id);

    @GET(BASE_URL+"/fav")
    Call<ResponseContainer<PropuestaResponse>> listaFavs();

    @POST(BASE_URL)
    Call<PropuestaCreateDto> createPropuesta();

    @POST(BASE_URL+"/fav/{id}")
    Call<PropuestaResponse> addFav(@Path("id") String id);

    @DELETE(BASE_URL+"/fav/{id}")
    Call<PropuestaResponse> deleteFav(@Path("id") String id);


}
