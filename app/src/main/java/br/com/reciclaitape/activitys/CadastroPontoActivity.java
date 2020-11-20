package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Markers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroPontoActivity extends AppCompatActivity {
    EditText nome,endereco,latitude,longitude;
    CheckBox papel,plastico,vidro;
    Button btnAcao;
    Boolean editar=false;
    Integer id_ponto=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ponto);
        carregaComponentes();
        try {
            id_ponto = getIntent().getIntExtra("id_ponto",0);
            if(id_ponto!=0){
                getSupportActionBar().setTitle("Editar de Pontos");
                btnAcao.setText("Atualizar");
                editar=true;
                buscaPontos(id_ponto);
            }
            else{
                getSupportActionBar().setTitle("Cadastro de Pontos");
            }
        }
        catch (Exception e){
            Log.e("ERRO",e.getMessage());
        }
    }
    public void onBackPressed() {
        Intent setIntent = new Intent(this, listagem_pontos.class);
        startActivity(setIntent);
    }
    public void buscaPontos(Integer id){
        try {
            Call<Markers> markersCall = ApiClient.getMarkersService().obterPonto(id);
            markersCall.enqueue(new Callback<Markers>() {
                @Override
                public void onResponse(Call<Markers> call, Response<Markers> response) {
                    nome.setText(response.body().getName());
                    endereco.setText(response.body().getAddress());
                    latitude.setText(String.valueOf(response.body().getLat()));
                    longitude.setText(String.valueOf(response.body().getLng()));
                    papel.setChecked(response.body().getPapel() == 1 ? true : false);
                    plastico.setChecked(response.body().getPlastico() == 1 ? true : false);
                    vidro.setChecked(response.body().getVidro() == 1 ? true : false);
                }

                @Override
                public void onFailure(Call<Markers> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            Log.e("ERRO",e.getMessage());
        }
    }
    public void carregaComponentes(){
        nome = (EditText) findViewById(R.id.txtNome);
        endereco = (EditText) findViewById(R.id.txtEndereco);
        latitude = (EditText) findViewById(R.id.txtLatitude);
        longitude = (EditText) findViewById(R.id.txtLongitude);
        papel = (CheckBox) findViewById(R.id.checkBoxPapel);
        plastico = (CheckBox) findViewById(R.id.checkBoxPlastico);
        vidro = (CheckBox) findViewById(R.id.checkBoxVidro);
        btnAcao = (Button) findViewById(R.id.btnPonto);
        btnAcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastroPonto();
            }
        });
    }
    public void CadastroPonto(){
        try {
            Markers markers = new Markers();
            markers.setName(nome.getText().toString());
            markers.setAddress(endereco.getText().toString());
            markers.setLat(Double.parseDouble(latitude.getText().toString()));
            markers.setLng(Double.parseDouble(longitude.getText().toString()));
            markers.setPapel(papel.isChecked() ? 1 : 0);
            markers.setPlastico(plastico.isChecked() ? 1 : 0);
            markers.setPapel(papel.isChecked() ? 1 : 0);
            markers.setVidro(vidro.isChecked() ? 1 : 0);
            if(editar==false){
                Call<ApiClient.DefaultResponse> responseBodyCall = ApiClient.getMarkersService().cadastroPontos(markers);
                responseBodyCall.enqueue(new Callback<ApiClient.DefaultResponse>() {
                    @Override
                    public void onResponse(Call<ApiClient.DefaultResponse> call, Response<ApiClient.DefaultResponse> response) {
                        if (response.body().getStatus().equals("ok")) {
                            Toast.makeText(CadastroPontoActivity.this, "Ponto Cadastrado!", Toast.LENGTH_SHORT).show();
                            abrirMapa();
                            Log.e("ERRO", "Cadastro");
                        }
                    }
                    @Override
                    public void onFailure(Call<ApiClient.DefaultResponse> call, Throwable t) {

                    }
                });
            }
            else{
                Call<ApiClient.DefaultResponse> defaultResponseCall = ApiClient.getMarkersService().atualizarPontos(markers,id_ponto);
                defaultResponseCall.enqueue(new Callback<ApiClient.DefaultResponse>() {
                    @Override
                    public void onResponse(Call<ApiClient.DefaultResponse> call, Response<ApiClient.DefaultResponse> response) {
                        Log.e("ERRO",response.body().getStatus());
                        if(response.body().getStatus().equals("ok")){
                            abrirMapa();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiClient.DefaultResponse> call, Throwable t) {
                        Log.e("ERRO","erro");
                    }
                });
            }
        }
        catch (Exception e){
            Log.e("ERRO",e.getLocalizedMessage());
        }
    }
    public void abrirMapa(){
        Intent intent = new Intent(this,mapa_reciclagem.class);
        startActivity(intent);
        finish();
    }
}