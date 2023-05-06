package com.example.registro_jornada_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button buttonLogin;
    EditText user;
    EditText password;
    Button textOlvido;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textOlvido = (Button) findViewById(R.id.olvido);
        user = (EditText) findViewById(R.id.boxEmail);
        password = (EditText) findViewById(R.id.boxPass);
        mAuth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("Boton", "Boton clickado");
                // Obtener los campos de user y password y llamar a la funcion de firebase de login.
                LogFirebase();
            }
        });

        textOlvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, PassOlvido.class);
                startActivity(intent);
            }
        });
    }

    public void LogFirebase() {
        String email = user.getText().toString();
        String password1 = password.getText().toString();
        if (email.isEmpty()) {
            user.setError("Campo vacio");
        } else if (!Utils.isValid(email)) {
            user.setError("El email no es válido");
        } else if (password1.isEmpty()) {
            password.setError("Campo vacio");

        } else if (password.length() <= 5) {
            password.setError("La contraseña tiene menos de 6 carácteres");
        } else {
            //metodo de firebase para login
            mAuth.signInWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //si funciona hace un Intent para cambiar de pantalla y sino saca error con toast
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Login fallido.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
/*
    public void LogFirebase(){
        mAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString() )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Cuando se completa la tarea de login, te devuelve un objeto task
                        // para ver si ha sido un login correcto o no

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LOGIN", "signInWithEmail:success");

                            // En el objeto mAuth tienes los datos de usuario.
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Login.this.getApplicationContext(), MainActivity.class);
                            if (user != null) {
                                String userName = user.getEmail();
                                if (userName != null){
                                    Log.d("USER", "USER " + userName);
                                    // Pasar a la siguiente pantalla

                                    intent.putExtra("USER", userName);
                                }
                            }
                             startActivity(intent);
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                            // Sacar un toast diciendo que no se a encontrado al usuario o lo que s
                            Toast.makeText(Login.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }


                    }
                });
    }*/


}