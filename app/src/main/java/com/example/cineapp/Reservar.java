package com.example.cineapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.ApiService;

import com.example.cineapp.models.Precio;
import com.example.cineapp.models.ReservaRequest;
import com.example.cineapp.models.ReservaResponse;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Reservar extends AppCompatActivity {

    private TextView txtPelicula, txtCine, txtSala, txtHora, txtPrecioUnitario, txtTotal;
    private EditText inputCantidad;
    private Button btnConfirmar;

    private int idHorario;
    private int idPersona = 1;

    private int precioUnitario = 0;
    private int precioSemana;
    private int precioFinde;

    private ApiService apiService; // ← Retrofit dentro de esta clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.77/cine_api/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        txtPelicula = findViewById(R.id.txtPelicula);
        txtCine = findViewById(R.id.txtCine);
        txtSala = findViewById(R.id.txtSala);
        txtHora = findViewById(R.id.txtHora);
        txtPrecioUnitario = findViewById(R.id.txtPrecioUnitario);
        txtTotal = findViewById(R.id.txtTotal);
        inputCantidad = findViewById(R.id.inputCantidad);
        btnConfirmar = findViewById(R.id.btnConfirmarReserva);
        String pelicula = getIntent().getStringExtra("pelicula");
        String cine = getIntent().getStringExtra("cine_nombre");
        String sala = getIntent().getStringExtra("sala");
        String hora = getIntent().getStringExtra("hora");
        idHorario = getIntent().getIntExtra("id_horario", -1);

        if (idHorario == -1) {
            Toast.makeText(this, "Error: No se recibió horario", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtPelicula.setText(pelicula);
        txtCine.setText(cine);
        txtSala.setText(sala);
        txtHora.setText(hora);
        cargarPrecios();

        configurarListeners();
    }

    private void cargarPrecios() {

        apiService.getPrecios().enqueue(new Callback<List<Precio>>() {
            @Override
            public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(Reservar.this, "Error al cargar precio", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Precio> precios = response.body();

                if (precios.size() < 2) {
                    Toast.makeText(Reservar.this, "Error: precios incompletos", Toast.LENGTH_SHORT).show();
                    return;
                }

                precioSemana = (int) precios.get(0).getValor();
                precioFinde  = (int) precios.get(1).getValor();

                calcularPrecioAutomatico();
            }

            @Override
            public void onFailure(Call<List<Precio>> call, Throwable t) {
                Toast.makeText(Reservar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calcularPrecioAutomatico() {
        Calendar cal = Calendar.getInstance();
        int dia = cal.get(Calendar.DAY_OF_WEEK);

        if (dia == Calendar.SATURDAY || dia == Calendar.SUNDAY) {
            precioUnitario = precioFinde;
        } else {
            precioUnitario = precioSemana;
        }

        txtPrecioUnitario.setText("$" + precioUnitario);
        actualizarTotal();
    }

    private void configurarListeners() {
        inputCantidad.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                actualizarTotal();
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        btnConfirmar.setOnClickListener(v -> enviarReserva());
    }

    private void actualizarTotal() {
        String cantStr = inputCantidad.getText().toString().trim();

        if (cantStr.isEmpty()) {
            txtTotal.setText("$0");
            return;
        }

        int cantidad = Integer.parseInt(cantStr);
        int total = cantidad * precioUnitario;

        txtTotal.setText("$" + total);
    }

    private void enviarReserva() {
        String cantStr = inputCantidad.getText().toString().trim();

        if (cantStr.isEmpty()) {
            Toast.makeText(this, "Ingrese cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantStr);

        ReservaRequest request = new ReservaRequest(
                idPersona,
                idHorario,
                cantidad
        );



        apiService.crearReserva(request).enqueue(new Callback<ReservaResponse>() {
            @Override
            public void onResponse(Call<ReservaResponse> call, Response<ReservaResponse> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(Reservar.this, "Error al crear reserva", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Reservar.this,
                        "Reserva creada. ID: " + response.body().getId(),
                        Toast.LENGTH_LONG).show();

                finish();
            }

            @Override
            public void onFailure(Call<ReservaResponse> call, Throwable t) {
                Toast.makeText(Reservar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
