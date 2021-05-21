package br.com.reciclaitape.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.reciclaitape.R;
import br.com.reciclaitape.classes.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link exibir_tutorial_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class exibir_tutorial_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String tutorial;
    private JSONObject dados;

    Util util = new Util();

    private TextView titulo,dados_autor;
    private ImageView banner_tutorial;

    public exibir_tutorial_fragment() {
        // Required empty public constructor
    }

    public static exibir_tutorial_fragment newInstance(String param1, String param2) {
        exibir_tutorial_fragment fragment = new exibir_tutorial_fragment();
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
        tutorial = this.getArguments().getString("tutorial");
        try {
            JSONObject obj = new JSONObject(tutorial);
            dados = obj;
        } catch (JSONException e) {
            util.mensagem(getView(),"Erro",false);
        }
        View view = inflater.inflate (R.layout.fragment_exibir_tutorial, container, false );
        carregar_componentes(view);
        return view;
    }
    public void carregar_componentes(View view){
        titulo = (TextView) view.findViewById(R.id.titulo_tutorial);
        dados_autor = (TextView) view.findViewById(R.id.dados_autor);
        banner_tutorial = (ImageView) view.findViewById(R.id.banner_tutorial);
        Log.e("ERRO",dados.toString());
        try {
            titulo.setText(dados.get("titulo").toString());
            /*DADOS DO AUTOR*/
            String autor,data=null,hora=null;
            autor=dados.get("autor").toString();
            data = util.convertar_data(dados.get("dataHora").toString());
            dados_autor.setText(String.format("Autor:%s Data:%s Hora:%s",autor,data,hora));





            if(dados.get("video").toString().trim()!=null||!dados.get("video").toString().trim().equals("")){

            }
            else{
                banner_tutorial.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load("https://reciclaitape.miqueiasfernando.work/img-tutorial/"+dados.get("imagem"))
                        .error(R.drawable.notfoundimage)
                        .into(banner_tutorial);
            }

        } catch (JSONException | ParseException e) {
            Log.e("ERRO","ERRO"+e.getMessage());
        }
    }
}