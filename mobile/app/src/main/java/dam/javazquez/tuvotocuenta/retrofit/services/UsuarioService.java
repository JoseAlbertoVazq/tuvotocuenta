package dam.javazquez.tuvotocuenta.retrofit.services;

import dam.javazquez.tuvotocuenta.dto.UserEditedDto;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {
    final String BASE_URL = "/users";

    @GET(BASE_URL+"/me")
    Call<UserResponse> getMe();

    @PUT(BASE_URL+"/{id}")
    Call<UserResponse> editUser(@Path("id") String id, @Body UserEditedDto edited);
}
