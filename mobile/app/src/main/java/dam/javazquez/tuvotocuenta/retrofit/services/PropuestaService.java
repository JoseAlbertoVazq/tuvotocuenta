package dam.javazquez.tuvotocuenta.retrofit.services;

import java.util.Map;

import dam.javazquez.tuvotocuenta.dto.PropuestaCreateDto;
import dam.javazquez.tuvotocuenta.responses.AfinResponse;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface PropuestaService {

    String BASE_URL = "/propuestas";

    @GET(BASE_URL)
    Call<ResponseContainer<PropuestaResponse>> listaPropuestas(@QueryMap Map<String, String> options);

    @GET(BASE_URL + "/{id}")
    Call<PropuestaResponse> getOnePropuesta(@Path("id") String id);

    @GET(BASE_URL + "/fav")
    Call<ResponseContainer<PropuestaResponse>> listaFavs();

    @GET(BASE_URL + "/afin")
    Call<ResponseContainer<AfinResponse>> afin();

    @GET(BASE_URL + "/propias")
    Call<ResponseContainer<PropuestaResponse>> listarPropias();

    @POST(BASE_URL)
    Call<PropuestaCreateDto> createPropuesta(@Body PropuestaCreateDto propuesta);

    @POST(BASE_URL + "/fav/{id}")
    Call<UserResponse> addFav(@Path("id") String id);

    @DELETE(BASE_URL + "/fav/{id}")
    Call<UserResponse> deleteFav(@Path("id") String id);

    @DELETE(BASE_URL + "/{id}")
    Call<PropuestaResponse> deletePropuesta(@Path("id") String id);


}
