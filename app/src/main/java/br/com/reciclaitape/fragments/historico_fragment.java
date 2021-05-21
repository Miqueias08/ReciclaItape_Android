package br.com.reciclaitape.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.activitys.activity_home;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Historico;
import br.com.reciclaitape.classes.Historico_Adapter;
import br.com.reciclaitape.classes.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class historico_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Util util = new Util();

    private String mParam1;
    private String mParam2;

    private ProgressBar progressBar;
    private View erro;
    private ListView dados_historico;


    public historico_fragment() {
        // Required empty public constructor
    }

    public static historico_fragment newInstance(String param1, String param2) {
        historico_fragment fragment = new historico_fragment();
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
        ((activity_home) getActivity()).desmarcar_drawer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.historico_fragment, container, false );
        ((activity_home) getActivity()).setTitle("Histórico");
        ((activity_home) getActivity()).nvDrawer.getMenu().getItem(0).getSubMenu().getItem(0).setCheckable(true);
        carrega_componentes(view);
        buscar_historico();
        return view;
    }
    public void carrega_componentes(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progresso);
        erro = (View) view.findViewById(R.id.erro);
        dados_historico = (ListView) view.findViewById(R.id.lstv_historico);
    }
    public void buscar_historico(){
        try {
            JSONObject dados = util.pegar_dados_usuario(getActivity());
            Call<List<Historico>> listCall = new ApiClient().getHistoricoService().busca_historico(Integer.parseInt(dados.get("id_usuario").toString()));
            listCall.enqueue(new Callback<List<Historico>>() {
                @Override
                public void onResponse(Call<List<Historico>> call, Response<List<Historico>> response) {
                    if(response.isSuccessful()) {
                        if(response.body().size()>0) {
                            List<Historico> historico_dados = response.body();
                            ArrayAdapter adapter = new Historico_Adapter(getActivity(), historico_dados);
                            dados_historico.setAdapter(adapter);
                            dados_historico.setVisibility(View.VISIBLE);
                            util.mensagem(getView(),"Dados Carregados!",false);
                        }
                        else{
                            util.mensagem(getView(),"Nenhuma Entrega Encontrada!",false);
                            nada_encontrado();
                        }
                    }
                    else{
                        util.erros(response,getView());
                    }
                    desabilitar_busca();
                }

                @Override
                public void onFailure(Call<List<Historico>> call, Throwable t) {
                    util.mensagem(getView(),t.getMessage(),true);
                }
            });
        }
        catch (Exception e){
            util.mensagem(getView(),e.getMessage(),true);
        }
    }
    public void desabilitar_busca(){
        progressBar.setVisibility(View.GONE);
    }
    public void nada_encontrado(){
        erro.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}