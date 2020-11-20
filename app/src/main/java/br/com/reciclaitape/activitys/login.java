package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private EditText txtEmail,txtSenha;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        carregaComponentes();
        verificaLogin();
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, mapa_reciclagem.class);
        startActivity(setIntent);
    }
    public void carregaComponentes(){
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EfetuaLogin();
            }
        });
    }
    public void verificaLogin(){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Credenciais",Context.MODE_PRIVATE);
        boolean logado = sharedPreferences.getBoolean("Logado",false);
        if(logado==true){
            Intent tela = new Intent(login.this, listagem_pontos.class);
            startActivity(tela);
            finish();
        }
    }
    public void EfetuaLogin(){
        if(validaLogin()==true){
            try {
                Login login = new Login();
                login.setEmail(txtEmail.getText().toString());
                login.setSenha(txtSenha.getText().toString());
                Call<ApiClient.LoginResponse> loginResponseCall = ApiClient.getLoginService().userlogin(login);
                loginResponseCall.enqueue(new Callback<ApiClient.LoginResponse>() {
                    @Override
                    public void onResponse(Call<ApiClient.LoginResponse> call, Response<ApiClient.LoginResponse> response) {
                        try {
                            if(response.body().getStatus().equals("ok")){
                                JSONObject dados = new JSONObject(new Gson().toJson(response.body()));
                                if(guardarUsuario(dados.toString()).equals(true)){
                                    Intent tela = new Intent(br.com.reciclaitape.activitys.login.this, listagem_pontos.class);
                                    startActivity(tela);
                                }
                            }
                        }
                        catch (Exception e){
                            Log.e("ERRO",e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiClient.LoginResponse> call, Throwable t) {
                        Toast.makeText(br.com.reciclaitape.activitys.login.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public Boolean validaLogin(){
        Integer erros=0;
        if(txtEmail.getText().toString().trim().length()==0){
            txtEmail.setError("Informe o email!");
            erros+=1;
        }
        if(txtSenha.getText().toString().trim().length()==0){
            txtEmail.setError("Informe a senha!");
            erros+=1;
        }
        if(erros==0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean guardarUsuario(String credencialUser){
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Credenciais", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPref = sharedPreferences.edit();
        sharedPref.putString("Credencial",credencialUser);
        sharedPref.putBoolean("Logado",true);
        if(sharedPref.commit()){
            return true;
        }
        else{
            return false;
        }
    }
}