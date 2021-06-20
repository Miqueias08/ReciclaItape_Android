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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.reciclaitape.R;
import br.com.reciclaitape.activitys.activity_home;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Usuarios;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login_fragment extends Fragment {
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

    public login_fragment() {
        // Required empty public constructor
    }

    public static login_fragment newInstance(String param1, String param2) {
        login_fragment fragment = new login_fragment();
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
        View view = inflater.inflate (R.layout.login_fragment, container, false );
        try {
            carrega_componentes(view);
            login();
            cadastro();
        }
        catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                            email.setText("");
                            senha.setText("");
                            util.mensagem(getView(),"Login Aprovado!",false);
                            /*GUARDANDO DADOS NA MEMORIA*/
                            util.setar_login_status(getContext(),true);
                            util.guardar_usuario(getContext(),dados.get("dados").toString());
                            util.setar_saldo_entrega(getContext(),Float.valueOf(dados.get("saldo_entrega").toString()));
                            /*TROCANDO DE TELA*/
                            ((activity_home) getActivity()).mostrar_drawerNav();
                            ((activity_home) getActivity()).setar_dados_usuario();
                            util_navegacao.fragmentClass= historico_fragment.class;
                            util_navegacao.navegacao_fragment(getFragmentManager());
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
                util_navegacao.fragmentClass = cadastro_usuario_fragment.class;
                util_navegacao.navegacao_fragment(getFragmentManager());
            }
        });
    }
}