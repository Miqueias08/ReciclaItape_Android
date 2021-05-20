package br.com.reciclaitape.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import br.com.reciclaitape.R;


public class minha_conta_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /*DRAWER*/
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle mToggle;
    Toolbar toolbar;

    public minha_conta_fragment() {
        // Required empty public constructor
    }

    public static minha_conta_fragment newInstance(String param1, String param2) {
        minha_conta_fragment fragment = new minha_conta_fragment();
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
        View view = inflater.inflate (R.layout.minha_conta_fragment, container, false );
        carrega_componentes(view);
        drawer_menu(view);
        return view;
    }
    public void carrega_componentes(View view){
        drawer_layout = (DrawerLayout) view.findViewById(R.id.drawer_minhaconta);
        //mToggle = new ActionBarDrawerToggle(this.getActivity(),drawer_layout,R.string.open,R.string.close);
        //mToggle.syncState();
        //((AppCompatActivity)getActivity()).getSupportActionBar();
        //NavigationView nvDrawer = (NavigationView) view.findViewById(R.id.NavigationView);

    }
    public void drawer_menu(View view){


    }
}