package com.example.cineapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.Precio;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarPrecio extends AppCompatActivity {

    EditText edtDescripcion, edtValor;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_precio);

        edtDescripcion = findViewById(R.id.edtDescripcionPrecio);
        edtValor = findViewById(R.id.edtValorPrecio);
        btnRegistrar = findViewById(R.id.btnRegistrarPrecio);

        ImageButton btnVolver = findViewById(R.id.btnVolverPrecio);
        btnVolver.setOnClickListener(v -> finish());

        btnRegistrar.setOnClickListener(v -> registrarPrecio());
    }

    private void registrarPrecio() {
        String descripcion = edtDescripcion.getText().toString().trim();
        String valor = edtValor.getText().toString().trim();

        if (descripcion.isEmpty() || valor.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Precio nuevo = new Precio(descripcion, valor);

        RetrofitClient.getApiService().insertarPrecio(nuevo)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(RegistrarPrecio.this, "Error en el servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(RegistrarPrecio.this, "Precio registrado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegistrarPrecio.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
