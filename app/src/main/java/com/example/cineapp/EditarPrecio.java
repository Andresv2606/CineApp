package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.Precio;
import com.example.cineapp.models.PrecioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPrecio extends AppCompatActivity {
    private EditText edtDescripcionPrecio, edtValorPrecio;
    private Button btnActualizarPrecio, btnCancelar;
    private String idPrecio;
    private int idCine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_precio);

        edtDescripcionPrecio = findViewById(R.id.edtDescripcionPrecio);
        edtValorPrecio = findViewById(R.id.edtValorPrecio);

        btnActualizarPrecio = findViewById(R.id.btnActualizarPrecio);
        btnCancelar = findViewById(R.id.btnCancelarEditar);

        findViewById(R.id.btnVolverEditar).setOnClickListener(v -> {
            String volverA = getIntent().getStringExtra("volver_a");
            if ("verprecios".equals(volverA)) {
                startActivity(new Intent(EditarPrecio.this, VerPrecios.class));
            }
            finish();
        });

        idPrecio = getIntent().getStringExtra("id_precio");
        String descripcion = getIntent().getStringExtra("descripcion");
        double valor = getIntent().getDoubleExtra("valor", 0.0);
        idCine = getIntent().getIntExtra("id_cine", 1);

        if (descripcion != null) edtDescripcionPrecio.setText(descripcion);
        edtValorPrecio.setText(String.valueOf(valor));
        btnCancelar.setOnClickListener(v -> finish());
        btnActualizarPrecio.setOnClickListener(v -> {

            String nuevaDesc = edtDescripcionPrecio.getText().toString().trim();
            String valorTexto = edtValorPrecio.getText().toString().trim();


            if (nuevaDesc.isEmpty() || valorTexto.isEmpty()) {
                Toast.makeText(EditarPrecio.this,
                        "Todos los campos son obligatorios",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nuevaDesc.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                Toast.makeText(EditarPrecio.this,
                        "La descripción solo debe contener letras",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (!valorTexto.matches("\\d+(\\.\\d+)?")) {
                Toast.makeText(EditarPrecio.this,
                        "El valor solo debe contener números",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            double nuevoValor;
            try {
                nuevoValor = Double.parseDouble(valorTexto);
            } catch (NumberFormatException e) {
                Toast.makeText(EditarPrecio.this,
                        "Valor inválido",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Precio precioActualizar = new Precio(idPrecio, nuevaDesc, nuevoValor, idCine);

            ApiService api = RetrofitClient.getApiService();
            api.actualizarPrecio(idPrecio, precioActualizar)
                    .enqueue(new Callback<PrecioResponse>() {
                        @Override
                        public void onResponse(Call<PrecioResponse> call, Response<PrecioResponse> response) {
                            if (response.isSuccessful()) {

                                new AlertDialog.Builder(EditarPrecio.this)
                                        .setTitle(" Actualización exitosa")
                                        .setMessage("Precio actualizado.")
                                        .setIcon(R.drawable.chulo)
                                        .setPositiveButton("Aceptar", (dialog, which) -> {
                                            setResult(RESULT_OK);
                                            finish();
                                        })
                                        .setCancelable(false)
                                        .show();

                            } else {
                                Toast.makeText(EditarPrecio.this,
                                        "Error al actualizar",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<PrecioResponse> call, Throwable t) {
                            Toast.makeText(EditarPrecio.this,
                                    "Error de conexión: " + t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
