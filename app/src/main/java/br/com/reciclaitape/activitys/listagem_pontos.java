package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Markers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listagem_pontos extends AppCompatActivity {
    private ListView lstvPontos1;
    private List<Markers> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_pontos);
        carregaComponentes();
        buscaPontos();
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, mapa_reciclagem.class);
        startActivity(setIntent);
    }
    public void carregaComponentes(){
        lstvPontos1 = (ListView) findViewById(R.id.lstvPontos);
        lstvPontos1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = String.valueOf(lstvPontos1.getItemAtPosition(i));
                Integer id =Integer.parseInt(text.split("]")[0].replace("[","").trim());
                Toast.makeText(listagem_pontos.this, id.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buscaPontos(){
        Call<List<Markers>> listCall = ApiClient.getMarkersService().getMarkers();
        listCall.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(Call<List<Markers>> call, Response<List<Markers>> response) {
                try {
                    markers = response.body();
                    ArrayAdapter<Markers> adapter = new ArrayAdapter<Markers>(listagem_pontos.this, android.R.layout.simple_list_item_1,markers);
                    lstvPontos1.setAdapter(adapter);
                }
                catch (Exception e){
                    Log.e("ERRO",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Markers>> call, Throwable t) {
                Toast.makeText(listagem_pontos.this, "Erro de Conexão", Toast.LENGTH_SHORT).show();
                Log.e("ERRO",t.getMessage());
            }
        });
    }
}