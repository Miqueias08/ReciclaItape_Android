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

import org.json.JSONObject;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Usuarios;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class cadastro_usuario_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btn_voltar,btn_cadastro;
    private EditText nome,email,senha,confirmar_senha;
    private ProgressBar carregar;

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
        View view = inflater.inflate (R.layout.cadastro_usuario_fragment, container, false );
        carrega_componentes(view);
        voltar();
        cadastro_usuario();
        return view;
    }
    public void carrega_componentes(View view){
        btn_voltar = (Button) view.findViewById(R.id.btnVoltar_usuario);
        btn_cadastro = (Button) view.findViewById(R.id.btnCadastro_usuario);
        nome = (EditText) view.findViewById(R.id.input_nome_cadastro);
        email = (EditText) view.findViewById(R.id.input_email_cadastro);
        senha = (EditText) view.findViewById(R.id.input_senha_cadastro);
        confirmar_senha = (EditText) view.findViewById(R.id.input_confirma_senha);
        carregar = (ProgressBar) view.findViewById(R.id.progresso_cadastro);
    }
    public void cadastro_usuario(){
        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valida_cadastro()==true) {
                    inicia_cadastro();
                    Usuarios usuarios = new Usuarios();
                    usuarios.setNome(nome.getText().toString());
                    usuarios.setEmail(email.getText().toString());
                    usuarios.setSenha(senha.getText().toString());
                    Call<JsonObject> jsonObjectCall = ApiClient.getCadastroUsuarioService().cadastro_usuario(usuarios);
                    jsonObjectCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            fim_cadastro();
                            if(response.isSuccessful()) {
                                /*CONVERSAO JSON*/
                                JSONObject dados = null;
                                try {
                                    dados = new JSONObject(new Gson().toJson(response.body()));
                                    if(dados.get("status").toString().equals("ok"))
                                    {
                                        nome.setText("");
                                        email.setText("");
                                        senha.setText("");
                                        confirmar_senha.setText("");
                                        util.mensagem(getView(),dados.get("mensagem").toString(),false);
                                    }
                                    else{
                                        util.mensagem(getView(),dados.get("mensagem").toString()+"\n"+
                                                dados.get("erro").toString(),true);
                                    }
                                } catch (Exception e) {
                                    util.mensagem(getView(),"Erro",true);
                                }
                            }
                            else{
                                util.erros(response,getView());
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            fim_cadastro();
                           util.mensagem(getView(),t.getMessage(),true);
                        }
                    });
                }
            }
        });
    }
    public Boolean valida_cadastro(){
        int erros =0;
        if(nome.getText().toString().trim().length()==0){
            erros+=1;
            nome.setError("Favor preencher o nome!");
        }
        if(email.getText().toString().trim().length()==0){
            erros+=1;
            email.setError("Favor preencher o email!");
        }
        else{

        }
        if(senha.getText().toString().trim().length()<8){
            erros+=1;
            senha.setError("A senha deve conter pelo menos 8 digitos!");
        }
        else{
            if(!senha.getText().toString().trim().equals(confirmar_senha.getText().toString().trim())){
                erros+=1;
                confirmar_senha.setError("A campo confirmar senha deve ser igual a senha!");
            }
        }
        if(erros==0){
            return true;
        }
        else{
            return false;
        }
    }
    public void inicia_cadastro(){
        carregar.setVisibility(View.VISIBLE);
        btn_voltar.setEnabled(false);
        btn_cadastro.setVisibility(View.GONE);
        nome.setEnabled(false);
        email.setEnabled(false);
        senha.setEnabled(false);
        confirmar_senha.setEnabled(false);
    }
    public void fim_cadastro(){
        carregar.setVisibility(View.GONE);
        btn_voltar.setEnabled(true);
        btn_cadastro.setVisibility(View.VISIBLE);
        nome.setEnabled(true);
        email.setEnabled(true);
        senha.setEnabled(true);
        confirmar_senha.setEnabled(true);
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