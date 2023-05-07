package com.example.registro_jornada_android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     private Menu menu;
     private Button botonEntrada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String message = intent.getStringExtra("USER");
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu, menu);
         EditText editTextHoraEntrada = (EditText) findViewById(R.id.editTextHoraEntrada);
         editTextHoraEntrada.setText("--:--:--");
        EditText editTextHoraSalida = (EditText) findViewById(R.id.editTextHoraSalida);
        editTextHoraEntrada.setText("--:--:--");
        botonEntrada = (Button) findViewById(R.id.buttonEntrada);
        botonEntrada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Map<String, Object> user=new HashMap<>();
                if(botonEntrada.getText().equals("Iniciar jornada")){
                Log.d("Boton", "Boton clickado");
                // Obtener fecha y hora actual
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    user=iniciarJornada(message, currentTime, currentDate);

                    botonEntrada.setText("Finalizar jornada");

                }
                else{
                    Log.d("Boton", "Boton clickado");
                    // Obtener fecha y hora actual
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    finalizarJornada(user, currentTime, currentDate);
                    botonEntrada.setEnabled(false);
                }

            }
        });
    }
    public Map<String, Object> iniciarJornada (String email, String horaEntrada, String fechaActual){
        Map<String, Object> user = new HashMap<>();
        user.put("user", email);
        user.put("horaEntrada", horaEntrada);
        user.put("fecha", fechaActual);
        user.put("horaSalida", "--:--:--");
        EditText editTextHoraEntrada = (EditText) findViewById(R.id.editTextHoraEntrada);
        editTextHoraEntrada.setText(horaEntrada);
// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error adding document", e);
                    }
                });
        return user;
    }
    public void finalizarJornada (Map<String, Object> user, String horaSalida, String fechaActual){
        user.put("horaSalida", horaSalida);
        EditText editTextHoraSalida = (EditText) findViewById(R.id.editTextHoraSalida);
        editTextHoraSalida.setText(horaSalida);
// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error adding document", e);
                    }
                });
    }
}
