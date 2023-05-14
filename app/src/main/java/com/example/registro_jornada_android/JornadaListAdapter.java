package com.example.registro_jornada_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JornadaListAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<JornadaDiaria> jornada;

    public JornadaListAdapter(Context context, ArrayList<JornadaDiaria> jornada){
        this.context=context;
        this.jornada=jornada;
    }

    @Override
    public int getCount() {
        return jornada.size();
    }

    @Override
    public Object getItem(int i) {
        return jornada.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // inflate the layout for each list row
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.listview_col, viewGroup, false);
        }

        // get current item to be displayed
        JornadaDiaria currentJornada = (JornadaDiaria) getItem(i);

        // get the TextView for item name and item description
        TextView textViewFecha = (TextView)
                view.findViewById(R.id.fecha);
        TextView textViewHoraEntrada = (TextView)
                view.findViewById(R.id.entrada);
        TextView textViewHoraSalida = (TextView)
                view.findViewById(R.id.salida);

        //sets the text for item name and item description from the current item object
        textViewFecha.setText(currentJornada.getFecha());
        textViewHoraEntrada.setText(currentJornada.getHoraEntrada());
        textViewHoraSalida.setText(currentJornada.getHoraSalida());

        return view;
    }
}

