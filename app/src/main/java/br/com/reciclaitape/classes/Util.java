package br.com.reciclaitape.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

public class Util {
    public void snackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void erros(Response response, View view){
        switch (response.code()){
            case 404:
                this.mensagem(view,"Servidor Indisponivel - Contate o Administrador \nErro:404",true);
                break;
            case 401:
                mensagem(view,"Token Recusado - Contate o Administrador \nErro:401",true);
                break;
            case 403:
                mensagem(view,"Token Recusado - Contate o Administrador \nErro:403",true);
                break;
            case 500:
                mensagem(view," Erro de Processamento no Servidor- Contate o Administrador \nErro:500",true);
                break;
            default:
                mensagem(view,"ERRO - Contate o Administrador \nErro:"+response.errorBody(),true);
        }
    }
    public void mensagem(View view,String mensagem,Boolean erro){
        String cor = erro==true?"#CD0000":"#6E8B3D";

        Snackbar snackbar = Snackbar.make(view,mensagem,Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.parseColor(cor));
        snackbar.show();
    }
    public JSONObject dados_usuario(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences("Credenciais", Context.MODE_PRIVATE);
        String usuario = preferences.getString("Credencial", "");
        JSONObject dados_usuario = null;
        try {
            dados_usuario = new JSONObject(usuario);
            return dados_usuario;
        } catch (JSONException e) {
            return null;
        }
    }
    public void setar_login_status(Context context,Boolean login){
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("Status_Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPref = sharedPreferences.edit();
        sharedPref.putBoolean("status",login);
    }
    public void guardar_usuario(Context context,String credencial){
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("Credenciais", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPref = sharedPreferences.edit();
        sharedPref.putString("Credencial",credencial);
        sharedPref.putBoolean("Logado",true);
    }
}
