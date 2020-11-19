package br.com.reciclaitape.api;

import br.com.reciclaitape.api.login.ServiceLogin;
import br.com.reciclaitape.api.markers.ServiceMarkers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    public static ServiceLogin getLoginService(){
        ServiceLogin loginService = retrofit().create(ServiceLogin.class);
        return loginService;
    }
    public static class LoginResponse{
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;
        private Integer id;
        private String email;
        private String senha;
    }
}
