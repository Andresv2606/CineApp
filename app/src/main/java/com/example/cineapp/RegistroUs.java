package com.example.cineapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUs extends AppCompatActivity {

    Button btn_registrar;
    ImageButton btnVolver;
    EditText txt_nombreReg, txt_apellidoReg, txt_emailReg, txt_usuarioReg, txt_contraseñaReg, txt_documentoReg, txt_telefonoReg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_us);

        txt_nombreReg = findViewById(R.id.txt_nombreReg);
        txt_apellidoReg = findViewById(R.id.txt_apellidoReg);
        txt_emailReg = findViewById(R.id.txt_emailReg);
        txt_usuarioReg = findViewById(R.id.txt_usuarioReg);
        txt_contraseñaReg = findViewById(R.id.txt_contraseñaReg);
        txt_documentoReg = findViewById(R.id.txt_documentoReg);
        txt_telefonoReg = findViewById(R.id.txt_telefonoReg);

        btn_registrar = findViewById(R.id.btn_registrar);
        btnVolver = findViewById(R.id.btnVolver);

        btn_registrar.setOnClickListener(v -> registrarUsuario());

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUs.this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void registrarUsuario() {
        Persona persona = new Persona(
                txt_nombreReg.getText().toString(),
                txt_apellidoReg.getText().toString(),
                txt_emailReg.getText().toString(),
                2, // Rol por defecto
                txt_usuarioReg.getText().toString(),
                txt_contraseñaReg.getText().toString(),
                txt_documentoReg.getText().toString(),
                txt_telefonoReg.getText().toString()
        );

        RetrofitClient.getApiService().registrarPersona(persona)
                .enqueue(new Callback<RegistroResponse>() {
                    @Override
                    public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {

                        try {
                            Log.e("API_ERROR", "Error: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("API_ERROR", "Error sin cuerpo");
                        }

                        if (!response.isSuccessful()) {
                            Toast.makeText(RegistroUs.this,
                                    "Error: " + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        RegistroResponse res = response.body();
                        Toast.makeText(RegistroUs.this,
                                res.getMessage(), Toast.LENGTH_LONG).show();

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(RegistroUs.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Evita que regresen al registro usando "atrás"
                        }, 1200);


                    }

                    @Override
                    public void onFailure(Call<RegistroResponse> call, Throwable t) {
                        Toast.makeText(RegistroUs.this,
                                "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}