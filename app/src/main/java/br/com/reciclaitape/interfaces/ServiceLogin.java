package br.com.reciclaitape.interfaces;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import br.com.reciclaitape.classes.Usuarios;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLogin {
    @POST("api/login")
    Call<JsonObject> login_usuario(@Body Usuarios login);
}
