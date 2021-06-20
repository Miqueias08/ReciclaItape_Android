package br.com.reciclaitape.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Cooperativas;
import br.com.reciclaitape.classes.CustomInfoWindowAdapter;
import br.com.reciclaitape.classes.Util;
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

    private TextInputLayout input_busca;
    private AutoCompleteTextView autoCompleteTextView;
   ArrayList<String> array_cooperativas;
   ArrayAdapter<String> array_adapter_cooperativas;
   ArrayList<Marker> array_location;
   String cooperativa;

   public Button remover_filtro,navegar,atualizar;
   private CharSequence[] plataformas;

   private Double latitude,longitude;

   Util util = new Util();

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
        //init();
        carregar_cooperativas();
        return view;
    }
    private void carrega_componentes(View view){
        input_busca = (TextInputLayout) view.findViewById(R.id.input_busca);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete);
        array_cooperativas = new ArrayList<>();
        array_location = new ArrayList<>();
        remover_filtro = (Button) view.findViewById(R.id.btn_remover);
        navegar = (Button) view.findViewById(R.id.btn_navegar);
        atualizar = (Button) view.findViewById(R.id.btn_atualizar);

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carrega_pontos(true);
                util.mensagem(getView(),"Cooperativas Atualizadas",false);

            }
        });

        remover_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               carrega_pontos(true);
               remover_filtro.setVisibility(View.GONE);
               autoCompleteTextView.setThreshold(0);
               autoCompleteTextView.clearListSelection();
               input_busca.setSelected(false);
               input_busca.getEditText().setText("");
               navegar.setVisibility(View.GONE);
            }
        });

        /*NAVEGAR*/
        plataformas = new CharSequence[]{
          "Google Maps",
          "Waze",
                "Cancelar"
        };

        navegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("Para qual plataforma deseja enviar a localização?");
                builder.setSingleChoiceItems(plataformas, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitude,longitude);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(intent);
                        }
                        else if(i==1){
                            try {
                                // Launch Waze to look for Hawaii:
                                String url = "https://waze.com/ul?ll="+latitude+","+longitude+"&navigate=yes";
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {
                                // If Waze is not installed, open it in Google Play:
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                                startActivity(intent);
                            }
                        }
                        else{
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.setIcon(R.drawable.ic_baseline_pin_drop_24);
                builder.show();
            }
        });
    }
    public void carregar_cooperativas(){
        array_adapter_cooperativas = new ArrayAdapter<>(getActivity(),R.layout.tv_entity,array_cooperativas);
        autoCompleteTextView.setAdapter(array_adapter_cooperativas);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cooperativa = array_adapter_cooperativas.getItem(i);
                cooperativa = cooperativa.split(":")[1];
                for (Marker m : array_location) {
                       mMap.clear();
                }
                carrega_pontos(false);
                remover_filtro.setVisibility(View.VISIBLE);
                navegar.setVisibility(View.VISIBLE);
            }
        });
    }
    public void google_maps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
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
        carrega_pontos(true);
    }
    public void carrega_pontos(Boolean geral){

        Call<List<Cooperativas>> listCall = ApiClient.getCooperativasService().busca_cooperativas();
        listCall.enqueue(new Callback<List<Cooperativas>>() {
            @Override
            public void onResponse(Call<List<Cooperativas>> call, Response<List<Cooperativas>> response) {
                array_cooperativas.clear();
                if(response.isSuccessful()){
                    List<Cooperativas> cooperativas = response.body();
                    if(cooperativas.size()>0){
                        for(Cooperativas cooperativas1:cooperativas){
                            try {
                                if(geral==true){
                                    String texto = "Endereço\n"+
                                            cooperativas1.getEndereco()+"\n"+
                                            "O ponto coleta o(s) seguintes tipo(s) de lixo\n"+
                                            cooperativas1.getMaterial_aceito();
                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(new LatLng(cooperativas1.getLat(),cooperativas1.getLng()))
                                            .title(cooperativas1.getRazao_social())
                                            .snippet(texto);
                                    mMap.addMarker(markerOptions);
                                    array_location.add(mMap.addMarker(markerOptions));
                                }
                                else{
                                    if(cooperativas1.getRazao_social().trim().equals(cooperativa.trim())){
                                        latitude = cooperativas1.getLat();
                                        longitude = cooperativas1.getLng();
                                        String texto = "Endereço\n"+
                                                cooperativas1.getEndereco()+"\n"+
                                                "O ponto coleta o(s) seguintes tipo(s) de lixo\n"+
                                                cooperativas1.getMaterial_aceito();
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(cooperativas1.getLat(),cooperativas1.getLng()))
                                                .title(cooperativas1.getRazao_social())
                                                .snippet(texto);
                                        mMap.addMarker(markerOptions);
                                        array_location.add(mMap.addMarker(markerOptions));
                                    }
                                }

                                array_cooperativas.add(String.valueOf(cooperativas1.getId_cooperativa())+"°: "+cooperativas1.getRazao_social());
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