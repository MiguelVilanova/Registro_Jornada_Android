package com.example.registro_jornada_android;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import androidx.biometric.BiometricPrompt;

import android.os.Bundle;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class Fingerprint extends AppCompatActivity {

    private Executor executor;
    public BiometricPrompt biometricPrompt;
    private androidx.biometric.BiometricPrompt.PromptInfo promptInfo;
    private static final int REQUEST_CODE = 1;
    ImageView fingerLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerrpint);
        fingerLogin=findViewById(R.id.fingerlogin);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("SecureLogin", "Se puede logear utilizando tus datos biométricos.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this,"El dispositivo no dispone de lector de huellas", Toast.LENGTH_SHORT);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this,"Hay un problema en el sensor", Toast.LENGTH_SHORT);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Si no hay huella configurada con este case se invoca la config
                //se podrían llegar a aceptar los credenciales del dispositivo
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Fingerprint.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Houston, tenemos un problema: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }


            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent=new Intent(Fingerprint.this,Login.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Fallo de autenticación",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login biométrico")
                .setSubtitle("Login utilizando credenciales biométricos")
                .setNegativeButtonText("boton")
                .build();

        

        fingerLogin.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }
}







