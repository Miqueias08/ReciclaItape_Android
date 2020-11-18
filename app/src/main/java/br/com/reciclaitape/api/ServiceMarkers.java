package br.com.reciclaitape.api;

import java.util.List;

import br.com.reciclaitape.classes.Markers;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceMarkers {
    @GET("obter/pontos")
    Call<List<Markers>> getMarkers();
}
