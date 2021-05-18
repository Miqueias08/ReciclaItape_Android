package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Cooperativas;
import br.com.reciclaitape.classes.CustomInfoWindowAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapa_reciclagem extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_reciclagem);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        float zoomLevel = 13.5f;

        mMap.getUiSettings().setMapToolbarEnabled(false);
        /*Inicia o mapa na cidade de Itapetininga*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.589158, -48.049099), zoomLevel), 200, null);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(),R.raw.map_style));
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(mapa_reciclagem.this));
        carrega_pontos();
    }
    public void carrega_pontos(){
        Call<List<Cooperativas>> listCall = ApiClient.getCooperativasService().busca_cooperativas();
        listCall.enqueue(new Callback<List<Cooperativas>>() {
            @Override
            public void onResponse(Call<List<Cooperativas>> call, Response<List<Cooperativas>> response) {
                if(response.isSuccessful()){
                    List<Cooperativas> cooperativas = response.body();
                    if(cooperativas.size()>0){
                        for(Cooperativas cooperativas1:cooperativas){
                            try {

                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cooperativas1.getLat(),cooperativas1.getLng()))
                                        .title("sasplpALSPalspAS")
                                        .snippet("Telefone:(15)"));
                            }
                            catch (Exception e){
                                Log.e("ERRO",e.getMessage());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cooperativas>> call, Throwable t) {
                Log.e("ERRO",t.getMessage().toString());
            }
        });
    }
}