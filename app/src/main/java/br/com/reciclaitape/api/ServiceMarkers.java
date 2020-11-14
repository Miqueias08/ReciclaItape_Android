package br.com.reciclaitape.api;

import java.util.List;

import br.com.reciclaitape.classes.markers;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceMarkers {
    @GET("obter/pontos")
    Call<List<markers>> getMarkers();
}
