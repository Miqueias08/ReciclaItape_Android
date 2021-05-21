package br.com.reciclaitape.interfaces;

import java.util.List;

import br.com.reciclaitape.classes.Ranking;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service_Ranking {
    @GET("api/ranking")
    Call<List<Ranking>> busca_ranking();
}
