package com.example.registro_jornada_android;
import static com.google.android.gms.common.util.CollectionUtils.mapOf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idDoc = "";
    private Menu menu;
    private Button botonEntrada, botonHistorico;
    String emailUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // cargarDatos();
        botonEntrada = (Button) findViewById(R.id.buttonEntrada);
        botonEntrada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (botonEntrada.getText().equals("Iniciar jornada")) {
                    Log.d("Boton", "Boton clickado");
                    // Obtener fecha y hora actual
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    iniciarJornada(emailUsuario, currentTime, currentDate);

                    botonEntrada.setText("Finalizar jornada");

                }
                else{
                    Log.d("Boton", "Boton clickado");
                    // Obtener fecha y hora actual
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    finalizarJornada(idDoc, currentTime, currentDate);
                    botonEntrada.setEnabled(false);
                }

            }
        });

        botonHistorico = (Button) findViewById(R.id.buttonHist);
        botonHistorico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), HistoricoRegistro.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }
    public void iniciarJornada (String email, String horaEntrada, String fechaActual){
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
                        //Se almacena el Id del doc añadido
                        idDoc =documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MainActivity", "Error adding document", e);
                    }
                });

    }
    public void finalizarJornada (String idDoc, String horaSalida, String fechaActual){
        EditText editTextHoraSalida = (EditText) findViewById(R.id.editTextHoraSalida);
        editTextHoraSalida.setText(horaSalida);
        //Actualizar la hora de salida con la hora registrada
        Log.d("MainActivity", "ID modificado: " + idDoc);
        db.collection("users").document(idDoc).update("horaSalida",horaSalida);
    }

    public void cargarDatos(){
        Log.d("MainActivity", "DocumentSnapshot added with ID: " + emailUsuario);
        EditText editTextHoraEntrada = (EditText) findViewById(R.id.editTextHoraEntrada);
        EditText editTextHoraSalida = (EditText) findViewById(R.id.editTextHoraSalida);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        db.collection("users").whereEqualTo("user", emailUsuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            return;
                        }
                        boolean existeRegistro=false;
                        String entrada="", salida="";
                        for (QueryDocumentSnapshot doc : value) {
                            //Comprobar si la fecha de hoy ha sido registrada:
                            if (doc.getString("fecha").equals(currentDate)){
                                existeRegistro=true;
                                entrada=doc.getString("horaEntrada");
                                salida=doc.getString("horaSalida");
                                idDoc =doc.getId();
                            }
                        }
                        //Si existe un registro en la fecha de hoy:
                        if (existeRegistro){
                            editTextHoraEntrada.setText(entrada);
                            editTextHoraSalida.setText(salida);
                            //Comprobar si se ha realizado la salida
                            if(salida.equals("--:--:--")){
                                botonEntrada.setText("Finalizar jornada");

                            }else{
                                //En caso de fichar entrada y salida, deshabilitar botón
                                botonEntrada.setText("Finalizar jornada");
                                botonEntrada.setEnabled(false);
                            }
                        }
                        else{
                            editTextHoraEntrada.setText("--:--:--");
                            editTextHoraSalida.setText("--:--:--");
                            botonEntrada.setText("Iniciar jornada");
                        }

                    }

                });




    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





