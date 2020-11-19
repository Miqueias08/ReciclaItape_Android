package br.com.reciclaitape.api.login;

import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceLogin {
    @POST("login")
    Call<ApiClient.LoginResponse> userlogin(@Body Login login);
}
