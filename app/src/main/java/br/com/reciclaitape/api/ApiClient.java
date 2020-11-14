package br.com.reciclaitape.api;

import java.util.List;

import br.com.reciclaitape.classes.markers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static String BASE_URL = "https://apirecicla.estanciaestrela.com.br/";

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
    public static ServiceMarkers getMarkersService(){
        ServiceMarkers markersService = retrofit().create(ServiceMarkers.class);
        return markersService;
    }
}
