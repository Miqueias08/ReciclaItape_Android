package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.markers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    List<markers> pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        Call<List<markers>> listCall = ApiClient.getMarkersService().getMarkers();
        listCall.enqueue(new Callback<List<markers>>() {
            @Override
            public void onResponse(Call<List<markers>> call, Response<List<markers>> response) {
                if(response.isSuccessful()){
                    for (markers pnt:response.body()){
                        try {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(pnt.getLat(),pnt.getLng())).title(pnt.getName()).snippet(pnt.getAddress()));
                        }
                        catch (Exception e) {
                            Log.e("ERRO", e.getMessage());
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<markers>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de Conexão", Toast.LENGTH_SHORT).show();
                Log.e("ERRO",t.getMessage());
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-23.589184, -48.048853)));


    }
}