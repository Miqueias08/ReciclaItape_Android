package br.com.reciclaitape.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.reciclaitape.R;

import java.util.List;

public class Historico_Adapter extends ArrayAdapter<Historico> {

    private Context context;
    private final List<Historico> historico;

    public Historico_Adapter(Context context, List<Historico> historico){
        super(context, R.layout.adapter_historico,historico);
        this.context=context;
        this.historico = historico;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Historico historico1 = this.historico.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_historico,parent,false);

        TextView Id = (TextView) rowView.findViewById(R.id.txtCodigoHistorico);
        TextView Peso = (TextView) rowView.findViewById(R.id.txtPesoEntrega);
        TextView Tipo_Material = (TextView) rowView.findViewById(R.id.txtTipoMaterial);
        TextView Cooperativa = (TextView) rowView.findViewById(R.id.txtCooperativa);


        Id.setText("Código:"+historico.get(position).getId());
        Peso.setText("Peso:"+historico.get(position).getPeso());
        Tipo_Material.setText("Tipo de Material:"+historico.get(position).getTipo_material());
        Cooperativa.setText("Cooperativa:"+historico.get(position).getCooperativa());


        return rowView;
    }
}
