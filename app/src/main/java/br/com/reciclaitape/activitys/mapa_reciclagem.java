package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
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

    /*Widgets*/
    private EditText mBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_reciclagem);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        carrega_componentes();
        init();
    }
    private void carrega_componentes(){
        mBusca = (EditText) findViewById(R.id.input_busca);
    }
    private void init(){
        mBusca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        ||actionId==EditorInfo.IME_ACTION_DONE
                        ||event.getAction()==KeyEvent.ACTION_DOWN
                        ||event.getAction()==KeyEvent.KEYCODE_ENTER
                ){
                    Log.e("ERRO","gdgdd");
                    /*Realiza a Busca*/
                    geolocalizacao();
                }
                return false;
            }
        });
    }
    private void geolocalizacao(){
        String searchString = mBusca.getText().toString();
        Geocoder geocoder = new Geocoder(mapa_reciclagem.this);
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocationName(searchString,1);
        }
        catch (IOException e){
            Log.e("ERRO","Geolocate IOException:"+e.getMessage());
        }
        if(addresses.size()>0){
            Address address = addresses.get(0);
            Log.d("ERRO", "Localizacoes: "+address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
        }
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
        mMap.clear();
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

                                String texto = "Endereço\n"+
                                        cooperativas1.getEndereco()+"\n"+
                                        "O ponto coleta o(s) seguintes tipo(s) de lixo\n"+
                                        cooperativas1.getMaterial_aceito();
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(new LatLng(cooperativas1.getLat(),cooperativas1.getLng()))
                                        .title(cooperativas1.getRazao_social())
                                        .snippet(texto);

                                mMap.addMarker(markerOptions);
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