package br.com.reciclaitape.interfaces;

import java.util.List;

import br.com.reciclaitape.classes.Cooperativas;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service_Cooperativas {
    @GET("api/cooperativas")
    Call<List<Cooperativas>> busca_cooperativas();
}
