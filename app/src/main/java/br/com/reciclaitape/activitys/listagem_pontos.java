package br.com.reciclaitape.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.AcoesListView;
import br.com.reciclaitape.classes.Markers;
import okhttp3.ResponseBody;
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
        getSupportActionBar().setTitle("Listagem de Pontos");
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
               acoesList(id);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_ponto,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.adiconarPonto:
                Intent intent = new Intent(this,CadastroPontoActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscaPontos(){
        Call<List<Markers>> listCall = ApiClient.getMarkersService().getMarkers();
        listCall.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(Call<List<Markers>> call, Response<List<Markers>> response) {
                try {
                    if(response.body().size()>0) {
                        markers = response.body();
                        ArrayAdapter<Markers> adapter = new ArrayAdapter<Markers>(listagem_pontos.this, android.R.layout.simple_list_item_1, markers);
                        lstvPontos1.setAdapter(adapter);
                    }
                    else{
                        List<String> dados = new ArrayList<String>();
                        dados.add("Não foi possivel localizar nenhum ponto!");
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listagem_pontos.this, android.R.layout.simple_list_item_1,dados);
                        lstvPontos1.setAdapter(arrayAdapter);
                    }
                }
                catch (Exception e){
                    Log.e("ERRO",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Markers>> call, Throwable t) {
                Toast.makeText(listagem_pontos.this, "Erro de Conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void acoesList(int id){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Ações");
        b.setMessage("O que deseja fazer?");
        b.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                editar(id);
            }
        });
        b.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletar(id);
            }
        });
        b.create().show();
    }
    public void deletar(Integer id){
        Call<ResponseBody> listCall = ApiClient.getMarkersService().deletarPonto(String.valueOf(id));
        listCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    buscaPontos();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    public void editar(Integer id){
        Intent intent = new Intent(this,CadastroPontoActivity.class);
        intent.putExtra("id_ponto",id);
        startActivity(intent);
    }
}