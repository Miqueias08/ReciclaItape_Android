package br.com.reciclaitape.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import br.com.reciclaitape.R;
import br.com.reciclaitape.classes.Util;
import br.com.reciclaitape.classes.Util_Navegacao;
import br.com.reciclaitape.fragments.login_fragment;
import br.com.reciclaitape.fragments.mapa_fragment;
import br.com.reciclaitape.fragments.historico_fragment;
import br.com.reciclaitape.fragments.ranking_fragment;
import br.com.reciclaitape.fragments.tutoriais_fragment;

public class activity_home extends AppCompatActivity{
    Util util = new Util();
    Util_Navegacao util_navegacao = new Util_Navegacao();
    /*WIDGET*/
    TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    public NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        carrega_componentes();
        drawer();
        esconder_drawerNav();
        setar_dados_usuario();
    }
    private void carrega_componentes(){
        tabLayout = findViewById(R.id.tablayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navegacao_inicial();
        navegacao_tabs();
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
    public void desmarcar_drawer(){
        int size = nvDrawer.getMenu().size();

        for (int i = 0; i < size; i++) {
            int siz1 = nvDrawer.getMenu().getItem(i).getSubMenu().size();
            for (int j = 0; i < siz1; i++) {
                nvDrawer.getMenu().getItem(i).getSubMenu().getItem(j).setChecked(false);
            }
        }
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
            case R.id.historico:
                util_navegacao.fragmentClass = historico_fragment.class;
                break;
            case R.id.Sair:
                logoff();
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
                            util_navegacao.fragmentClass = historico_fragment.class;
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
                            util_navegacao.fragmentClass = historico_fragment.class;
                        }
                        else{
                            util_navegacao.fragmentClass = login_fragment.class;
                        }
                        break;
                }
                util_navegacao.navegacao_fragment(getSupportFragmentManager());
            }
        });
    }
    public void setar_dados_usuario(){
        try {
            JSONObject dados = util.pegar_dados_usuario(this);
            if(dados!=null) {
                //Buscando os Componentes
                NavigationView navigationView = (NavigationView) findViewById(R.id.NavigationView);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = (TextView) headerView.findViewById(R.id.username);
                TextView navEmail = (TextView) headerView.findViewById(R.id.emailuser);
                navUsername.setText(dados.getString("nome"));
                navEmail.setText(dados.getString("email"));
                TextView pontuacao = (TextView) headerView.findViewById(R.id.total_entrega);
                pontuacao.setText(String.valueOf(util.get_saldo_entrega(this)) + " KG");
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Erro ao coletar as informações!", Toast.LENGTH_SHORT).show();
            Log.e("ERRO",e.getMessage());
        }

    }
    public void logoff(){
        util.setar_saldo_entrega(this,0);
        util.setar_login_status(this,false);
        util.guardar_usuario(this,null);
        util_navegacao.fragmentClass = login_fragment.class;
        esconder_drawerNav();
        util_navegacao.navegacao_fragment(getSupportFragmentManager());
    }
}