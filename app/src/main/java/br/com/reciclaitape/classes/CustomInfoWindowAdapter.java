package br.com.reciclaitape.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.reciclaitape.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View view;
    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }

    private void redowWindowText(Marker cooperativas, View view){
        String razao_social = cooperativas.getTitle();
        TextView razao_s = view.findViewById(R.id.razao_social);
        razao_s.setText(razao_s.getText().toString());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        redowWindowText(marker,view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        redowWindowText(marker,view);
        return view;
    }
}
