package br.com.reciclaitape.classes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import br.com.reciclaitape.R;
import br.com.reciclaitape.fragments.exibir_tutorial_fragment;
import br.com.reciclaitape.fragments.tutoriais_fragment;

public class Tutorial_Adapter extends ArrayAdapter<Tutoriais> {
    Util util = new Util();
    Util_Navegacao util_navegacao = new Util_Navegacao();
    private Context context;
    private final List<Tutoriais> tutoriais;

    public Tutorial_Adapter(Context context, List<Tutoriais> tutoriais){
        super(context, R.layout.adapter_tutorial,tutoriais);
        this.context=context;
        this.tutoriais = tutoriais;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Tutoriais tutorial1 = this.tutoriais.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_tutorial,parent,false);

        ImageView image_tutorial = (ImageView) rowView.findViewById(R.id.imagemTutorial);

        Picasso.get()
                .load("https://reciclaitape.miqueiasfernando.work/img-tutorial/"+tutoriais.get(position).getImagem())
                .error(R.drawable.notfoundimage)
                .into(image_tutorial);

        TextView Titulo = (TextView) rowView.findViewById(R.id.txtTitulo);
        TextView Autor = (TextView) rowView.findViewById(R.id.txtAutor);
        TextView Data = (TextView) rowView.findViewById(R.id.txtData);


        Titulo.setText(tutoriais.get(position).getTitulo());
        Autor.setText(tutoriais.get(position).getAutor());
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(tutoriais.get(position).getDataHora());
            Data.setText(date1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.tutorial_geral);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //util_navegacao.fragmentClass = exibir_tutorial_fragment.class;
                //util_navegacao.navegacao_fragment(((AppCompatActivity)context).getSupportFragmentManager());

                Bundle bundle = new Bundle();
                bundle.putString("tutorial",tutoriais.get(position).toString());
                exibir_tutorial_fragment fragInfo = new exibir_tutorial_fragment();
                fragInfo.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameContent,fragInfo).commit();
            }
        });


        return rowView;
    }
}
