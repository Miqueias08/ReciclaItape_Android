package br.com.reciclaitape.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.reciclaitape.R;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;


public class cadastro_usuario_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btn_voltar,btn_cadastro;
    Util util = new Util();
    Util_Navegacao util_navegacao = new Util_Navegacao();

    public cadastro_usuario_fragment() {
        // Required empty public constructor
    }

    public static cadastro_usuario_fragment newInstance(String param1, String param2) {
        cadastro_usuario_fragment fragment = new cadastro_usuario_fragment();
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
        View view = inflater.inflate (R.layout.fragment_exibir_tutorial, container, false );
        carrega_componentes(view);
        voltar();
        return view;
    }
    public void carrega_componentes(View view){
        btn_voltar = (Button) view.findViewById(R.id.btnVoltar_usuario);
    }
    public void voltar(){
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util_navegacao.fragmentClass = login_fragment.class;
                util_navegacao.navegacao_fragment(getFragmentManager());
            }
        });
    }

}