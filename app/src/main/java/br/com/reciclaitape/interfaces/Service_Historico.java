package br.com.reciclaitape.interfaces;

import java.util.List;

import br.com.reciclaitape.classes.Cooperativas;
import br.com.reciclaitape.classes.Historico;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service_Historico {
    @GET("api/historico/{idUser}")
    Call<List<Historico>> busca_historico(@Path("idUser") Integer id);
}
