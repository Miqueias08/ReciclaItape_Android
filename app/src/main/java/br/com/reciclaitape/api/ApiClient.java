package br.com.reciclaitape.api;

import br.com.reciclaitape.interfaces.Service_Cadastro_Usuario;
import br.com.reciclaitape.interfaces.Service_Cooperativas;
import br.com.reciclaitape.interfaces.ServiceLogin;
import br.com.reciclaitape.interfaces.Service_Historico;
import br.com.reciclaitape.interfaces.Service_Ranking;
import br.com.reciclaitape.interfaces.Service_Tutorial;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String BASE_URL = "https://reciclaitape.miqueiasfernando.work/";

    private  static Retrofit retrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    public static ServiceLogin getLoginService(){
        ServiceLogin loginService = retrofit().create(ServiceLogin.class);
        return loginService;
    }
    public static Service_Cooperativas getCooperativasService(){
        Service_Cooperativas cooperativas = retrofit().create(Service_Cooperativas.class);
        return cooperativas;
    }
    public static Service_Historico getHistoricoService(){
        Service_Historico historico = retrofit().create(Service_Historico.class);
        return historico;
    }
    public static Service_Ranking getRankingService(){
        Service_Ranking service_ranking = retrofit().create(Service_Ranking.class);
        return service_ranking;
    }
    public static Service_Tutorial getTutorialService(){
        Service_Tutorial service_tutorial = retrofit().create(Service_Tutorial.class);
        return service_tutorial;
    }
    public static Service_Cadastro_Usuario getCadastroUsuarioService(){
        Service_Cadastro_Usuario service_cadastro_usuario = retrofit().create(Service_Cadastro_Usuario.class);
        return service_cadastro_usuario;
    }
}
