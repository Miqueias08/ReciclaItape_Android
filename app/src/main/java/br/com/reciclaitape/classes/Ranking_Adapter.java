package br.com.reciclaitape.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.reciclaitape.R;

public class Ranking_Adapter extends ArrayAdapter<Ranking> {

    private Context context;
    private final List<Ranking> ranking;

    public Ranking_Adapter(Context context, List<Ranking> ranking){
        super(context,R.layout.adapter_ranking,ranking);
        this.context=context;
        this.ranking = ranking;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Ranking ranking1 = this.ranking.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_ranking,parent,false);

        TextView Posicao = (TextView) rowView.findViewById(R.id.txtPosicao);
        TextView Usuario = (TextView) rowView.findViewById(R.id.txtUsuario);
        TextView Peso = (TextView) rowView.findViewById(R.id.txtPesoEntrega_ranking);


        Posicao.setText("Posição:"+ranking.get(position).getPosicao());
        Usuario.setText("Usuário:"+ranking.get(position).getUsuario());
        Peso.setText("Peso de Entrega(KG):"+ranking.get(position).getQuantidade_entregue());


        return rowView;
    }
}
