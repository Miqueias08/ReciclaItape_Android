package br.com.reciclaitape.api.markers;

import java.util.List;

import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Markers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceMarkers {
    @GET("obter/pontos")
    Call<List<Markers>> getMarkers();
    @DELETE("pontos/{id}")
    Call<ResponseBody> deletarPonto(@Path("id") String id);
    @POST("pontos/cadastro")
    Call<ApiClient.DefaultResponse> cadastroPontos(@Body Markers markers);
}
