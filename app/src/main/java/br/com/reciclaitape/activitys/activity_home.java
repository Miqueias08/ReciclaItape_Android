package br.com.reciclaitape.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
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
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        carrega_componentes();
        navegacao_inicial();
        navegacao_tabs();
        drawer();
        esconder_drawerNav();
    }
    private void carrega_componentes(){
        tabLayout = findViewById(R.id.tablayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
    }
    public void drawer(){
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nvDrawer = (NavigationView) findViewById(R.id.NavigationView);
        setupDrawerContent(nvDrawer);
        /*ESCODER DRAWER E NAVBAR*/
        esconder_drawerNav();
    }
    public void esconder_drawerNav(){
        getSupportActionBar().hide();
        drawerLayout.closeDrawers();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    public void mostrar_drawerNav(){
        getSupportActionBar().show();
        drawerLayout.closeDrawers();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
    /*AO CLICAR PARA ABRIR DRAWER*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void selectItemDrawer(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.HoraMaquina:
                //util_navegacao.fragmentClass = listar_usuarios.class;
                break;
            case R.id.Sair:
                //logoff();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }
        menuItem.setCheckable(true);
        setTitle(menuItem.getTitle());
        util_navegacao.navegacao_fragment(getSupportFragmentManager());
    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
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
                esconder_drawerNav();
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
                        /*MINHA CONTA*/
                        if(util.get_loginStatus(getApplicationContext())==true){
                            mostrar_drawerNav();
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