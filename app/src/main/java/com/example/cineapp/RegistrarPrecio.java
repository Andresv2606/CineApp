package com.example.cineapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.Precio;
import com.example.cineapp.models.PrecioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarPrecio extends AppCompatActivity {

    EditText txtDescripcion, txtValor;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_precio);

        txtDescripcion = findViewById(R.id.edtDescripcionPrecio);
        txtValor = findViewById(R.id.edtValorPrecio);
        btnGuardar = findViewById(R.id.btnRegistrarPrecio);


        findViewById(R.id.btnVolverPrecio).setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> guardarPrecio());
    }

    private void guardarPrecio() {
        String descripcion = txtDescripcion.getText().toString().trim();
        String valorTexto = txtValor.getText().toString().trim();

        if (descripcion.isEmpty() || valorTexto.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorTexto);

        Precio precio = new Precio(descripcion, valor, 1);

        RetrofitClient.getApiService().registrarPrecio(precio)
                .enqueue(new Callback<PrecioResponse>() {
                    @Override
                    public void onResponse(Call<PrecioResponse> call, Response<PrecioResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistrarPrecio.this, "Precio registrado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<PrecioResponse> call, Throwable t) {
                        Toast.makeText(RegistrarPrecio.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("API", "Error: " + t.getMessage());
                    }
                });
    }
}
