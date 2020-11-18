package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.reciclaitape.R;
import br.com.reciclaitape.api.ApiClient;
import br.com.reciclaitape.classes.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail,txtSenha;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        carregaComponentes();
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
                        
                    }

                    @Override
                    public void onFailure(Call<ApiClient.LoginResponse> call, Throwable t) {

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
}