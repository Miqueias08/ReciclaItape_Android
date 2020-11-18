package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import br.com.reciclaitape.R;

public class CadastroPontoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_ponto);
    }
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this,MapaActivity.class);
        startActivity(setIntent);
    }
}