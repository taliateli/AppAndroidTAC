package tac.com.appandroidtac;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

import tac.com.appandroidtac.model.Posto;

public class PostoArrayAdapter extends ArrayAdapter<Posto> {

    private final Context context;
    private final List<Posto> postos;

    public PostoArrayAdapter(Context context, List<Posto> postos) {
        super(context, R.layout.activity_posto_array, postos);
        this.context = context;
        this.postos = postos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_posto_array, parent, false);
        Posto p = postos.get(position);
        TextView tvNome = (TextView) rowView.findViewById(R.id.nomePosto);
        tvNome.setText(p.getNome());
        TextView tvLatitude = (TextView) rowView.findViewById(R.id.latitudePosto);
        tvLatitude.setText(String.valueOf(p.getLatitude()));
        TextView tvLongitude = (TextView) rowView.findViewById(R.id.longitudePosto);
        tvLongitude.setText(String.valueOf(p.getLongitude()));
        return rowView;
    }
}
