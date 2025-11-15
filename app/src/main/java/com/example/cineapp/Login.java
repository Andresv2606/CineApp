package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {

    EditText txt_persona, txt_contrasena;
    Button btnIniciarSesion, btnRegistrate, btncontraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txt_persona = findViewById(R.id.txt_persona);
        txt_contrasena = findViewById(R.id.txt_contrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrate = findViewById(R.id.btnRegistrate);
        btncontraseña = findViewById(R.id.btncontraseña);


        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());

        btnRegistrate.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RegistroUs.class);
            startActivity(intent);
        });


        btncontraseña.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, contrasena.class);
            startActivity(intent);
        });
    }

    private void iniciarSesion() {

        String usuario = txt_persona.getText().toString();
        String contrasena = txt_contrasena.getText().toString();

        LoginRequest request = new LoginRequest(usuario, contrasena);

        RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this,
                            "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error login: " + response.code());
                    return;
                }

                LoginResponse login = response.body();

                if (login == null || login.getUser() == null) {
                    Toast.makeText(Login.this,
                            "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario user = login.getUser();

                Toast.makeText(Login.this,
                        "Bienvenido " + user.getNombre() + " " + user.getApellido(),
                        Toast.LENGTH_LONG).show();

                // Ir a pantalla principal
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("API", "Fallo login: " + t.getMessage());
                Toast.makeText(Login.this,
                        "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}