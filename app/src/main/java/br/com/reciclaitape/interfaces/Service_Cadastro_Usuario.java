package br.com.reciclaitape.interfaces;

import com.google.gson.JsonObject;

import java.util.List;

import br.com.reciclaitape.classes.Ranking;
import br.com.reciclaitape.classes.Usuarios;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service_Cadastro_Usuario {
    @POST("api/cadastro/usuario")
    Call<JsonObject> cadastro_usuario(@Body Usuarios usuarios);
}
