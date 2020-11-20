package br.com.reciclaitape.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Markers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapa_reciclagem extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    List<Markers> pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Pontos de Coleta");
        setContentView(R.layout.mapa_reciclagem);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        float zoomLevel = 12.0f;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        Call<List<Markers>> listCall = ApiClient.getMarkersService().getMarkers();
        listCall.enqueue(new Callback<List<Markers>>() {
            @Override
            public void onResponse(Call<List<Markers>> call, Response<List<Markers>> response) {
                if(response.isSuccessful()){
                    for (Markers pnt:response.body()){
                        try {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(pnt.getLat(),pnt.getLng())).title(pnt.getName()).snippet(pnt.getAddress()));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pnt.getLat(),pnt.getLng()), zoomLevel), 200, null);
                        }
                        catch (Exception e) {
                            Log.e("ERRO", e.getMessage());
                        }
                    }
                }
                else{
                    Toast.makeText(mapa_reciclagem.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Markers>> call, Throwable t) {
                Toast.makeText(mapa_reciclagem.this, "Erro de Conexão", Toast.LENGTH_SHORT).show();
                Log.e("ERRO",t.getMessage());
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-23.589184, -48.048853)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
                Login();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void Login(){
        Intent login = new Intent(this, br.com.reciclaitape.activitys.login.class);
        startActivity(login);
        finish();
    }
}