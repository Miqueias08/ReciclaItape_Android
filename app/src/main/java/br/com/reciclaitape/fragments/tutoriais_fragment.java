package br.com.reciclaitape.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Historico;
import br.com.reciclaitape.classes.Historico_Adapter;
import br.com.reciclaitape.classes.Tutoriais;
import br.com.reciclaitape.classes.Tutorial_Adapter;
import br.com.reciclaitape.classes.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tutoriais_fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Util util = new Util();

    private ProgressBar progressBar;
    private View erro;
    private ListView dados_historico;

    private androidx.appcompat.widget.Toolbar toolbar;

    public tutoriais_fragment() {
        // Required empty public constructor
    }

    public static tutoriais_fragment newInstance(String param1, String param2) {
        tutoriais_fragment fragment = new tutoriais_fragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.tutoriais_fragment, container, false );
        carrega_componentes(view);
        busca_tutoriais();
        setHasOptionsMenu(true);
        return view;
    }
    public void carrega_componentes(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progresso_tutorial);
        erro = (View) view.findViewById(R.id.erro_tutorial);
        dados_historico = (ListView) view.findViewById(R.id.lstv_tutorial);
        toolbar =view.findViewById(R.id.toolbar_tutorial);
        toolbar_navegacao();
    }
    public void toolbar_navegacao(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.toString()){
                    case "Atualizar":
                        atualizar_tutoriais();
                        break;
                }
                return false;
            }
        });
    }
    public void atualizar_tutoriais(){
        try {
            dados_historico.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            busca_tutoriais();
        }
        catch (Exception e){

        }
    }
    public void busca_tutoriais(){
        try {
            Call<List<Tutoriais>> listCall = new ApiClient().getTutorialService().busca_tutorial();
            listCall.enqueue(new Callback<List<Tutoriais>>() {
                @Override
                public void onResponse(Call<List<Tutoriais>> call, Response<List<Tutoriais>> response) {
                    if(response.isSuccessful()) {
                        if(response.body().size()>0) {
                            List<Tutoriais> tutorial_dados = response.body();
                            ArrayAdapter adapter = new Tutorial_Adapter(getActivity(), tutorial_dados);
                            dados_historico.setAdapter(adapter);
                            dados_historico.setVisibility(View.VISIBLE);
                            util.mensagem(getView(),"Dados Carregados!",false);
                        }
                        else{
                            util.mensagem(getView(),"Nenhum Tutorial Encontrada!",false);
                            nada_encontrado();
                        }
                    }
                    else{
                        util.erros(response,getView());
                    }
                    desabilitar_busca();
                }

                @Override
                public void onFailure(Call<List<Tutoriais>> call, Throwable t) {
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