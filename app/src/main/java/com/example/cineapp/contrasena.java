package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.CambiarPassRequest;
import com.example.cineapp.models.CambiarPassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class contrasena extends AppCompatActivity {
    Button btnEnviar, btnRestablecer;
    EditText txtCorreo, txtCodigo;
    ImageButton btnFlecha;
    TextView tvVolverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena);

        btnFlecha = findViewById(R.id.btnVolverBusPel);
        tvVolverLogin = findViewById(R.id.tvVolverLogin);

        // Botones de recuperar contraseña
        btnEnviar = findViewById(R.id.btnConfirmar);
        btnRestablecer = findViewById(R.id.btnRestablecer);
        txtCorreo = findViewById(R.id.txtContraseña);
        txtCodigo = findViewById(R.id.txtCodigo);

        txtCodigo.setVisibility(View.GONE);   // o INVISIBLE
        btnRestablecer.setVisibility(View.GONE);   // o INVISIBLE

        btnEnviar.setOnClickListener(v -> {
            String correo = txtCorreo.getText().toString();

            CambiarPassRequest request = new CambiarPassRequest(correo);

            RetrofitClient.getApiService().solicitar(request).enqueue(new Callback<CambiarPassResponse>() {
                @Override
                public void onResponse(Call<CambiarPassResponse> call, Response<CambiarPassResponse> response) {
                    if (!response.isSuccessful()) {
                        // Error HTTP como 400, 404, 500, etc.
                        if (response.code() == 404) {
                            Toast.makeText(contrasena.this,
                                    "El correo es incorrecto o no existe", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(contrasena.this,
                                    "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        btnRestablecer.setVisibility(View.GONE);
                        txtCodigo.setVisibility(View.GONE);
                        return;
                    } else {
                        CambiarPassResponse body = response.body();

                        if (body == null) {
                            Toast.makeText(contrasena.this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (body.isSuccess()) {
                            Toast.makeText(contrasena.this, body.getMessage(), Toast.LENGTH_SHORT).show();
                            btnRestablecer.setVisibility(View.VISIBLE);
                            txtCodigo.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(contrasena.this, body.getError(), Toast.LENGTH_SHORT).show();
                            Log.e("API", "ERROR: " + body.getError());
                            btnRestablecer.setVisibility(View.GONE);
                            txtCodigo.setVisibility(View.GONE);
                        }
                    }


                }

                @Override
                public void onFailure(Call<CambiarPassResponse> call, Throwable t) {
                    Log.e("API", "Fallo login: " + t.getMessage());
                    Toast.makeText(contrasena.this,
                            "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnRestablecer.setOnClickListener(v -> {
            String correo = txtCorreo.getText().toString();
            String codigo = txtCodigo.getText().toString();

            CambiarPassRequest request = new CambiarPassRequest(correo, codigo);

            RetrofitClient.getApiService().verificar(request).enqueue(new Callback<CambiarPassResponse>() {
                @Override
                public void onResponse(Call<CambiarPassResponse> call, Response<CambiarPassResponse> response) {
                    if (!response.isSuccessful()) {
                        // Error HTTP como 400, 404, 500, etc.
                        if (response.code() == 404) {
                            Toast.makeText(contrasena.this,
                                    "El correo es incorrecto o no existe", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(contrasena.this,
                                    "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                        btnRestablecer.setVisibility(View.GONE);
                        txtCodigo.setVisibility(View.GONE);
                        return;
                    } else {
                        CambiarPassResponse body = response.body();

                        if (body == null) {
                            Toast.makeText(contrasena.this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (body.isSuccess()) {
                            Toast.makeText(contrasena.this, body.getMessage(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(contrasena.this, RestablecerContrasena.class);
                            intent.putExtra("correo", correo);
                            intent.putExtra("codigo", codigo);
                            startActivity(intent);
                        } else {
                            Toast.makeText(contrasena.this, body.getError(), Toast.LENGTH_SHORT).show();
                            Log.e("API", "ERROR: " + body.getError());
                        }
                    }


                }

                @Override
                public void onFailure(Call<CambiarPassResponse> call, Throwable t) {
                    Log.e("API", "Fallo login: " + t.getMessage());
                    Toast.makeText(contrasena.this,
                            "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnFlecha.setOnClickListener(v -> {
            Intent intent = new Intent(contrasena.this, Login.class);
            startActivity(intent);
            finish();
        });

        tvVolverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(contrasena.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}