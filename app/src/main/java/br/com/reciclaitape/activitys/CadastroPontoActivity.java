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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ponto);
        getSupportActionBar().setTitle("Cadastro de Pontos");
        carregaComponentes();
    }
    public void carregaComponentes(){
        nome = (EditText) findViewById(R.id.txtNome);
        endereco = (EditText) findViewById(R.id.txtEndereco);
        latitude = (EditText) findViewById(R.id.txtLatitude);
        longitude = (EditText) findViewById(R.id.txtLatitude);
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
            Call<ApiClient.DefaultResponse> responseBodyCall = ApiClient.getMarkersService().cadastroPontos(markers);
           responseBodyCall.enqueue(new Callback<ApiClient.DefaultResponse>() {
               @Override
               public void onResponse(Call<ApiClient.DefaultResponse> call, Response<ApiClient.DefaultResponse> response) {
                   Log.e("ERRO",response.body().getStatus());
                   if(response.body().getStatus().equals("ok")){
                       Toast.makeText(CadastroPontoActivity.this, "Ponto Cadastrado!", Toast.LENGTH_SHORT).show();
                       abrirMapa();
                   }
               }

               @Override
               public void onFailure(Call<ApiClient.DefaultResponse> call, Throwable t) {

               }
           });

        }
        catch (Exception e){
            Log.e("ERRO",e.getMessage());
        }
    }
    public void abrirMapa(){
        Intent intent = new Intent(this,mapa_reciclagem.class);
        startActivity(intent);
        finish();
    }
}