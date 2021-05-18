package br.com.reciclaitape.api;

import br.com.reciclaitape.interfaces.Service_Cooperativas;
import br.com.reciclaitape.interfaces.ServiceLogin;
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
}
