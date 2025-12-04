package com.example.cineapp;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.cineapp.models.PrecioResponse;
import com.example.cineapp.models.ReservaRequest;
import com.example.cineapp.models.ReservaResponse;

import java.io.File;
import java.io.FileOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reservar extends AppCompatActivity {

    private TextView txtPelicula, txtCine, txtSala, txtHora, txtPrecioUnitario, txtTotal;
    private EditText inputCantidad;
    private Button btnConfirmar;

    private int idHorario;
    private int idPersona;

    private int precioUnitario = 0;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        solicitarPermisos();
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        idPersona = prefs.getInt("id_persona", -1);

        if (idPersona == -1) {
            Toast.makeText(this, "Error: no se encontró el usuario", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService = RetrofitClient.getApiService();

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

    private void solicitarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }
    }


    private void cargarPrecios() {
        apiService.getPrecioHorario(idHorario).enqueue(new Callback<PrecioResponse>() {
            @Override
            public void onResponse(Call<PrecioResponse> call, Response<PrecioResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(Reservar.this, "Error al cargar precio", Toast.LENGTH_SHORT).show();
                    return;
                }

                PrecioResponse precioResp = response.body();
                precioUnitario = (int) precioResp.getPrecioUnitario();

                txtPrecioUnitario.setText("$" + precioUnitario);
                actualizarTotal();
            }

            @Override
            public void onFailure(Call<PrecioResponse> call, Throwable t) {
                Toast.makeText(Reservar.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ===== INPUT DE CANTIDAD =====
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

    // ===== ENVIAR RESERVA =====
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

                ReservaResponse resp = response.body();

                Toast.makeText(Reservar.this,
                        "¡Reserva creada exitosamente!",
                        Toast.LENGTH_LONG).show();


                generarPDF(
                        String.valueOf(resp.getIdReserva()),
                        resp.getPersona(), // ← NOMBRE DE QUIEN RESERVÓ
                        txtPelicula.getText().toString(),
                        txtHora.getText().toString(),
                        String.valueOf(cantidad),
                        String.valueOf(precioUnitario),
                        txtTotal.getText().toString()
                );


                finish();
            }

            @Override
            public void onFailure(Call<ReservaResponse> call, Throwable t) {
                Toast.makeText(Reservar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ===== GENERAR PDF =====
    private void generarPDF(String idReserva, String persona, String pelicula, String horario,
                            String cantidad, String precioUnitario, String total) {

        PdfDocument pdf = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();
        Paint headerPaint = new Paint();


        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 600, 1).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();


        headerPaint.setColor(0xFFE53935); // Rojo CineMax
        canvas.drawRect(0, 0, 400, 80, headerPaint);


        titlePaint.setColor(0xFFFFFFFF);
        titlePaint.setTextSize(26);
        titlePaint.setFakeBoldText(true);
        canvas.drawText("CineMax", 140, 50, titlePaint);

        int y = 110;


        Paint titulo = new Paint();
        titulo.setTextSize(20);
        titulo.setFakeBoldText(true);
        canvas.drawText("COMPROBANTE DE RESERVA", 60, y, titulo);
        y += 30;


        paint.setColor(0xFFBDBDBD);
        canvas.drawLine(20, y, 380, y, paint);
        y += 30;


        paint.setColor(0xFF000000);
        paint.setTextSize(15);

        canvas.drawText("Reserva N°: " + idReserva, 20, y, paint); y += 25;
        canvas.drawText("Cliente: " + persona, 20, y, paint); y += 25;
        canvas.drawText("Película: " + pelicula, 20, y, paint); y += 25;
        canvas.drawText("Horario: " + horario, 20, y, paint); y += 25;
        canvas.drawText("Boletos: " + cantidad, 20, y, paint); y += 25;
        canvas.drawText("Precio unitario: $" + precioUnitario, 20, y, paint); y += 25;
        canvas.drawText("Total a pagar: " + total, 20, y, paint); y += 35;


        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        canvas.drawText("¡Gracias por su compra en Cine Max!", 100, y, paint);

        pdf.finishPage(page);


        String filename = "reserva_" + idReserva + "_" + System.currentTimeMillis() + ".pdf";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/" + filename;

        try {
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            pdf.writeTo(fos);
            fos.close();

            Toast.makeText(this, "PDF guardado como\n" + filename, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        pdf.close();
    }

}
