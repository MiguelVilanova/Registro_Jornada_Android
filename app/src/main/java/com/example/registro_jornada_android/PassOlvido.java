package com.example.registro_jornada_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PassOlvido extends AppCompatActivity {

    private FirebaseAuth auth;
    private Toolbar toolbar;
    EditText email;
    Button buttonContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_olvido);

<<<<<<< HEAD
        toolbar = (Toolbar) findViewById(R.id.toolbarOlvidoPass);
=======
        toolbar = findViewById(R.id.toolbarOlvidoPass);
>>>>>>> origin/rama-patri
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonContinuar = findViewById(R.id.botonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reestablecerPass();
            }
        });
    }
        private void reestablecerPass(){
            auth = FirebaseAuth.getInstance();
            email = (EditText) findViewById(R.id.usuario);
            String emailAddress = email.getText().toString();
            if (emailAddress.isEmpty()) {
                email.setError("Campo vacio");
            } else if (!Utils.isValid(emailAddress)) {
                email.setError("El email no es válido");
            }
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(PassOlvido.this, "Se ha enviado un email para reestablecer la contraseña.",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PassOlvido.this, Login.class);
                                startActivity(intent);
                            } else {
                                // Ha ocurrido un error al enviar el correo electrónico
                                Toast.makeText(PassOlvido.this, "Error al enviar el correo electrónico.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
/*
        */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD

=======
>>>>>>> origin/rama-patri
}