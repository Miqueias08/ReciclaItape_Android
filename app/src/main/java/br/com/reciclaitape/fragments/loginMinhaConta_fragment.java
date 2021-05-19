package br.com.reciclaitape.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Cooperativas;
import br.com.reciclaitape.classes.Usuarios;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class loginMinhaConta_fragment extends Fragment {
    Util util = new Util();
    /*NAVEGACAO*/
    Util_Navegacao util_navegacao = new Util_Navegacao();

    /*WIDGETS*/
    private EditText email,senha;
    private Button cadastro,login;
    private ProgressBar progressBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public loginMinhaConta_fragment() {
        // Required empty public constructor
    }

    public static loginMinhaConta_fragment newInstance(String param1, String param2) {
        loginMinhaConta_fragment fragment = new loginMinhaConta_fragment();
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
        View view = inflater.inflate (R.layout.login_minha_conta_fragment, container, false );
        carrega_componentes(view);
        login();
        cadastro();
        return view;
    }
    public void carrega_componentes(View view){
        email = (EditText) view.findViewById(R.id.input_txtEmail);
        senha = (EditText) view.findViewById(R.id.input_txtSenha);
        cadastro = (Button) view.findViewById(R.id.btnCadastro);
        login = (Button) view.findViewById(R.id.btnLogin);
        progressBar = (ProgressBar) view.findViewById(R.id.progresso_login);
    }
    public void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicia_login();
                login_api();
            }
        });
    }
    public void login_api(){
        Usuarios usuarios = new Usuarios();
        usuarios.setEmail(email.getText().toString());
        usuarios.setSenha(senha.getText().toString());
        Call<JsonObject> api = ApiClient.getLoginService().login_usuario(usuarios);
        api.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    /*CONVERSAO JSON*/
                    JSONObject dados = null;
                    try {
                        dados = new JSONObject(new Gson().toJson(response.body()));
                        /*PROCESSA LOGIN*/
                        if(dados.get("status").equals("ok")){
                            util.mensagem(getView(),"Login Aprovado!",false);
                        }
                        else{
                            util.mensagem(getView(),"Erro ao realizar o login\n"
                                    +dados.get("mensagem"),true);
                        }
                    } catch (JSONException e) {
                        util.mensagem(getView(),"Erro ao realizar o login",true);
                    }
                    fim_login();
                }
                else{
                    fim_login();
                    util.erros(response,getView());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                fim_login();
                util.mensagem(getView(),"Erro ao realizar o login\n"
                        +t.getMessage(),true);
            }
        });
    }
    public void inicia_login(){
        progressBar.setVisibility(View.VISIBLE);
        cadastro.setClickable(false);
        login.setClickable(false);
        email.setEnabled(false);
        senha.setEnabled(false);
    }
    public void fim_login(){
        progressBar.setVisibility(View.GONE);
        cadastro.setClickable(true);
        login.setClickable(true);
        email.setEnabled(true);
        senha.setEnabled(true);
    }

    public void cadastro(){
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util_navegacao.fragmentClass = cadastro_fragment.class;
                util_navegacao.navegacao_fragment(getFragmentManager());
            }
        });
    }
}