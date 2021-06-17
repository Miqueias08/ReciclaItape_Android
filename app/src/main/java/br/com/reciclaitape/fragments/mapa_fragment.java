package br.com.reciclaitape.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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

public class mapa_fragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /*GOOGLE MAPS*/
    private GoogleMap mMap;
    private EditText mBusca;

    public mapa_fragment() {
        // Required empty public constructor
    }

    public static mapa_fragment newInstance(String param1, String param2) {
        mapa_fragment fragment = new mapa_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.mapa_fragment, container, false );
        carrega_componentes(view);
        google_maps();
        carrega_pontos();
        init();
        return view;
    }
    private void carrega_componentes(View view){
        mBusca = (EditText) view.findViewById(R.id.input_busca);
    }
    public void google_maps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    private void init(){
        mBusca.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Log.e("ERRO",String.valueOf(actionId));
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        ||actionId==EditorInfo.IME_ACTION_DONE
                        ||keyEvent.getAction()==KeyEvent.ACTION_DOWN
                        ||keyEvent.getAction()==KeyEvent.KEYCODE_ENTER){
                    /*Realiza a Busca*/
                    geolocalizacao();
                }
                return false;
            }
        });
    }
    private void geolocalizacao(){
        String searchString = mBusca.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
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
        /*Inicia o mapa_fragment na cidade de Itapetininga*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.589158, -48.049099), zoomLevel), 200, null);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.map_style));
        mMap.clear();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));
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