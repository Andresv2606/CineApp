package com.example.cineapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.Cine;
import com.example.cineapp.models.Precio;
import com.example.cineapp.models.PrecioResponse;
import com.example.cineapp.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarPrecio extends AppCompatActivity {

    EditText txtDescripcion, txtValor;
    Spinner spinnerCines;
    Button btnGuardar;

    List<Cine> listaCines = new ArrayList<>();
    int idCineSeleccionado = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_precio);

        txtDescripcion = findViewById(R.id.edtDescripcionPrecio);
        txtValor = findViewById(R.id.edtValorPrecio);
        spinnerCines = findViewById(R.id.spinnerCines);
        btnGuardar = findViewById(R.id.btnRegistrarPrecio);

        findViewById(R.id.btnVolverPrecio).setOnClickListener(v -> finish());

        cargarCines();

        btnGuardar.setOnClickListener(v -> guardarPrecio());
    }

    private void cargarCines() {

        RetrofitClient.getApiService().getCines().enqueue(new Callback<List<Cine>>() {
            @Override
            public void onResponse(Call<List<Cine>> call, Response<List<Cine>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(RegistrarPrecio.this, "Error al cargar cines", Toast.LENGTH_SHORT).show();
                    return;
                }

                listaCines = response.body();
                Log.d("CARGA", "Cines cargados: " + listaCines.size());

                List<String> nombres = new ArrayList<>();
                for (Cine c : listaCines) {
                    nombres.add(c.getNombre());
                }

                ArrayAdapter<String> adapter =
                        new ArrayAdapter<>(RegistrarPrecio.this,
                                android.R.layout.simple_spinner_dropdown_item, nombres);

                spinnerCines.setAdapter(adapter);

                spinnerCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idCineSeleccionado = listaCines.get(position).getId_cine();
                        Log.d("CINE", "Seleccionado ID: " + idCineSeleccionado);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
            }

            @Override
            public void onFailure(Call<List<Cine>> call, Throwable t) {
                Log.e("API", "Error cargando cines: " + t.getMessage());
                Toast.makeText(RegistrarPrecio.this, "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("CARGA", "Cines cargados: " + listaCines.size());
    }

    private void guardarPrecio() {

        String descripcion = txtDescripcion.getText().toString().trim();
        String valorTexto = txtValor.getText().toString().trim();

        if (descripcion.isEmpty() || valorTexto.isEmpty() || idCineSeleccionado == -1) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorTexto);

        // Crear objeto Precio para enviar al backend
        Precio precio = new Precio(descripcion, valor, idCineSeleccionado);

        Log.d("ENVIO", "JSON = " + new com.google.gson.Gson().toJson(precio));

        RetrofitClient.getApiService().registrarPrecio(precio)
                .enqueue(new Callback<PrecioResponse>() {
                    @Override
                    public void onResponse(Call<PrecioResponse> call, Response<PrecioResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistrarPrecio.this, "Precio registrado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegistrarPrecio.this, "Error al guardar", Toast.LENGTH_SHORT).show();
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