package br.com.reciclaitape.interfaces;

import java.util.List;

import br.com.reciclaitape.classes.Ranking;
import br.com.reciclaitape.classes.Tutoriais;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service_Tutorial {
    @GET("api/tutoriais")
    Call<List<Tutoriais>> busca_tutorial();
}
