package com.example.registro_jornada_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoricoRegistro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String emailUsuario;
    List<String> listJornada = new ArrayList<>();
    ListView listViewJornada;//, listViewEntrada, listViewSalida;
    ArrayAdapter<String> mAdapterJornada;//, mAdapterEntrada, mAdapterSalida;

    String mensajeUsuario;

    TextView mensajeBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historico_registro);

        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        listViewJornada = findViewById(R.id.listaJornada);

        mensajeBienvenida = findViewById(R.id.mensajeUser);

        mensajeBienvenida.setText("¡Hola, "+ emailUsuario +"!");

        //Mostrar histórico del usuario actual:
        mostrarHistorico();

    }

    private void mostrarHistorico() {

        //Mostrar el histórico de cada usuario
        //Ordenados por fecha: orderBy("fecha", Query.Direction.DESCENDING)

        db.collection("users")
                .whereEqualTo("user", emailUsuario).orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }

                        listJornada.clear();

                        Log.d("HistoricoRegistro","Mostrando datos de: " + emailUsuario);

                        for (QueryDocumentSnapshot doc : value) {
                            listJornada.add(doc.getString("fecha")+" " +doc.getString("horaEntrada")+" "+doc.getString("horaSalida"));
                        }
                        Log.d("HistoricoRegistro","Total de registros: " + listJornada.size());
                        if (listJornada.size() == 0) {
                            listViewJornada.setAdapter(null);
                        } else {
                            mAdapterJornada = new ArrayAdapter<String>(HistoricoRegistro.this,  R.layout.jornada, R.id.jornada, listJornada);
                            listViewJornada.setAdapter(mAdapterJornada);

                        }
                    }

                });


    }

}
