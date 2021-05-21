package br.com.reciclaitape.classes;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import br.com.reciclaitape.R;

public class Util_Navegacao {
    public Fragment fragment = null;
    public Class fragmentClass=null;

    public void navegacao_fragment(FragmentManager fragmentManager){
        Log.e("ERRO",fragmentClass.getName());
        if(fragmentClass!=null){
            try {
                Fragment fragment_existe=null;
                FragmentTransaction ft = fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                /*Procura Fragment*/
                List<Fragment> fragments_lista = fragmentManager.getFragments();
                for(int i=0;i<fragments_lista.size();i++){
                    if(fragments_lista.get(i).getTag()==fragmentClass.getName()){
                        fragment_existe = fragments_lista.get(i);
                    }
                    /*Esconder Fragment*/
                    if(fragments_lista.get(i).isVisible() & fragments_lista.get(i).getTag()!=fragmentClass.getName()){
                        Fragment fragment_esconder = fragments_lista.get(i);
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                                .hide(fragment_esconder) //or hide(somefrag)
                                .commit();
                    }
                }
                /*Verifica se fragmento existe*/
                if(fragment_existe!=null){
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .show(fragment_existe) //or hide(somefrag)
                            .commit();
                }
                else{
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragmentManager.beginTransaction().add(R.id.frameContent,fragment,fragmentClass.getName()).commit();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void voltar_tabs(FragmentManager fragmentManager,Class aClass,FragmentActivity fragmentActivity){
        //((HomeActivity) fragmentActivity).habilitar_tabs();
        //this.fragmentClass = aClass;
        //this.navegacao_fragment(fragmentManager);
    }

}
