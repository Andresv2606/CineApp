package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.CambiarPassRequest;
import com.example.cineapp.models.CambiarPassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerContrasena extends AppCompatActivity {
    Button btnConfirmar;
    EditText txtContraseña, txtContraseñaConf;
    ImageButton btnFlecha;
    TextView tvVolverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasena);

        btnFlecha = findViewById(R.id.btnVolverBusPel);
        tvVolverLogin = findViewById(R.id.tvVolverLogin);

        // Botones de recuperar contraseña
        btnConfirmar = findViewById(R.id.btnConfirmar);
        txtContraseña = findViewById(R.id.txtContraseña);
        txtContraseñaConf = findViewById(R.id.txtContraseñaCon);

        btnConfirmar.setOnClickListener(v -> {
            String correo = getIntent().getStringExtra("correo");
            String codigo = getIntent().getStringExtra("codigo");

            String pass1 = txtContraseña.getText().toString();
            String pass2 = txtContraseñaConf.getText().toString();

            if (pass1.equals(pass2)) {
                CambiarPassRequest request = new CambiarPassRequest(correo, codigo, pass1);

                RetrofitClient.getApiService().cambiar(request).enqueue(new Callback<CambiarPassResponse>() {
                    @Override
                    public void onResponse(Call<CambiarPassResponse> call, Response<CambiarPassResponse> response) {
                        if (!response.isSuccessful()) {
                            // Error HTTP como 400, 404, 500, etc.
                            if (response.code() == 404) {
                                Toast.makeText(RestablecerContrasena.this,
                                        "El correo es incorrecto o no existe", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RestablecerContrasena.this,
                                        "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } else {
                            CambiarPassResponse body = response.body();

                            if (body == null) {
                                Toast.makeText(RestablecerContrasena.this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (body.isSuccess()) {
                                Toast.makeText(RestablecerContrasena.this, body.getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RestablecerContrasena.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RestablecerContrasena.this, body.getError(), Toast.LENGTH_SHORT).show();
                                Log.e("API", "ERROR: " + body.getError());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CambiarPassResponse> call, Throwable t) {
                        Log.e("API", "Fallo login: " + t.getMessage());
                        Toast.makeText(RestablecerContrasena.this,
                                "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(RestablecerContrasena.this,
                        "Las contraseñas deben ser las mismas", Toast.LENGTH_SHORT).show();
            }

        });

        btnFlecha.setOnClickListener(v -> {
            Intent intent = new Intent(RestablecerContrasena.this, Login.class);
            startActivity(intent);
            finish();
        });

        tvVolverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RestablecerContrasena.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}