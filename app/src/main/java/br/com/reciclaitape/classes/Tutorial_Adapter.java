package br.com.reciclaitape.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.reciclaitape.R;

public class Tutorial_Adapter extends ArrayAdapter<Tutoriais> {

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
                .load("https://reciclaitape.miqueiasfernando.work/img-tutorial/teste.jpg")
                .error(R.drawable.exit)
                .into(image_tutorial);

        //TextView Id = (TextView) rowView.findViewById(R.id.txtCodigoHistorico);
        //TextView Peso = (TextView) rowView.findViewById(R.id.txtPesoEntrega);
        //TextView Tipo_Material = (TextView) rowView.findViewById(R.id.txtTipoMaterial);
        //TextView Cooperativa = (TextView) rowView.findViewById(R.id.txtCooperativa);


        //Id.setText("Código:"+historico.get(position).getId());
        //Peso.setText("Peso:"+historico.get(position).getPeso());
        //Tipo_Material.setText("Tipo de Material:"+historico.get(position).getTipo_material());
        //Cooperativa.setText("Cooperativa:"+historico.get(position).getCooperativa());


        return rowView;
    }
}
