package br.com.reciclaitape.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import org.json.JSONObject;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Historico;
import br.com.reciclaitape.classes.Historico_Adapter;
import br.com.reciclaitape.classes.Ranking;
import br.com.reciclaitape.classes.Ranking_Adapter;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.interfaces.Service_Ranking;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ranking_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Util util = new Util();
    private Toolbar toolbar;

    private ProgressBar progressBar;
    private View erro;
    private ListView dados_ranking;

    private androidx.appcompat.widget.Toolbar toolbar1;

    public ranking_fragment() {
        // Required empty public constructor
    }

    public static ranking_fragment newInstance(String param1, String param2) {
        ranking_fragment fragment = new ranking_fragment();
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
        View view = inflater.inflate (R.layout.ranking_fragment, container, false);
        carrega_componentes(view);
        busca_ranking();
        return view;
    }
    public void carrega_componentes(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progresso_ranking);
        erro = (View) view.findViewById(R.id.erro_ranking);
        dados_ranking = (ListView) view.findViewById(R.id.lstv_ranking);
        toolbar1 =view.findViewById(R.id.toolbar_ranking);
        toolbar_navegacao();
    }
    public void toolbar_navegacao(){
        toolbar1.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.toString()){
                    case "Atualizar":
                        habilita_busca();
                        busca_ranking();
                        break;
                }
                return false;
            }
        });
    }
    public void busca_ranking(){
        try {
            Call<List<Ranking>> listCall = new ApiClient().getRankingService().busca_ranking();
            listCall.enqueue(new Callback<List<Ranking>>() {
                @Override
                public void onResponse(Call<List<Ranking>> call, Response<List<Ranking>> response) {
                    if(response.isSuccessful()) {
                        if(response.body().size()>0) {
                            List<Ranking> ranking_dados = response.body();
                            ArrayAdapter adapter = new Ranking_Adapter(getActivity(), ranking_dados);
                            dados_ranking.setAdapter(adapter);
                            dados_ranking.setVisibility(View.VISIBLE);
                            util.mensagem(getView(),"Ranking Carregado!",false);
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
                public void onFailure(Call<List<Ranking>> call, Throwable t) {
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
        dados_ranking.setVisibility(View.VISIBLE);
    }
    public void habilita_busca(){
        progressBar.setVisibility(View.VISIBLE);
        dados_ranking.setVisibility(View.GONE);
    }
    public void nada_encontrado(){
        dados_ranking.setVisibility(View.GONE);
        erro.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}