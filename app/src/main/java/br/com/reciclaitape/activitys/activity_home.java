package br.com.reciclaitape.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import br.com.reciclaitape.R;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;
import br.com.reciclaitape.fragments.login_fragment;
import br.com.reciclaitape.fragments.mapa_fragment;
import br.com.reciclaitape.fragments.minha_conta_fragment;
import br.com.reciclaitape.fragments.ranking_fragment;
import br.com.reciclaitape.fragments.tutoriais_fragment;

public class activity_home extends AppCompatActivity{
    Util util = new Util();
    Util_Navegacao util_navegacao = new Util_Navegacao();
    /*WIDGET*/
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        carrega_componentes();
        navegacao_inicial();
        navegacao_tabs();
    }
    private void carrega_componentes(){
        tabLayout = findViewById(R.id.tablayout);
    }
    private void navegacao_inicial(){
        util_navegacao.fragmentClass = mapa_fragment.class;
        util_navegacao.navegacao_fragment(getSupportFragmentManager());
        tabLayout.setScrollPosition(1,0f,true);
    }
    public void navegacao_tabs(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        util_navegacao.fragmentClass = tutoriais_fragment.class;
                        break;
                    case 1:
                        util_navegacao.fragmentClass = mapa_fragment.class;
                        break;
                    case 2:
                        util_navegacao.fragmentClass = ranking_fragment.class;
                        break;
                    case 3:
                        if(util.get_loginStatus(getApplicationContext())==true){
                            util_navegacao.fragmentClass = minha_conta_fragment.class;
                        }
                        else{
                            util_navegacao.fragmentClass = login_fragment.class;
                        }
                        break;
                }
                util_navegacao.navegacao_fragment(getSupportFragmentManager());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        util_navegacao.navegacao_fragment(getSupportFragmentManager());
    }

}