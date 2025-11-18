package com.example.cineapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.Precio;
import com.example.cineapp.models.PrecioResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrecioAdapter extends RecyclerView.Adapter<PrecioAdapter.ViewHolder> {

    private List<Precio> lista;
    private int rolUsuario;



    public PrecioAdapter(List<Precio> lista) {
        this.lista = lista;
        this.rolUsuario = rolUsuario;
    }
    public PrecioAdapter(List<Precio> lista, int rolUsuario) {
        this.lista = lista;
        this.rolUsuario = rolUsuario;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_precio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Precio precioActual = lista.get(position);

        holder.descripcion.setText(precioActual.getDescripcion());
        holder.valor.setText("$ " + precioActual.getValor());


        if (rolUsuario != 1) {
            holder.btnEditar.setVisibility(View.GONE);
            holder.btnEliminar.setVisibility(View.GONE);
        }

        // EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditarPrecio.class);
            intent.putExtra("volver_a", "verprecios");
            intent.putExtra("id_precio", precioActual.getId_precio());
            intent.putExtra("descripcion", precioActual.getDescripcion());
            intent.putExtra("valor", precioActual.getValor());
            intent.putExtra("id_cine", precioActual.getId_cine());
            v.getContext().startActivity(intent);
        });

        // ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Seguro quieres eliminar este precio?")
                    .setPositiveButton("Sí", (dialog, which) ->
                            eliminarPrecio(v, precioActual.getId_precio(), position)
                    )
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void eliminarPrecio(View v, String idPrecio, int position) {
        ApiService api = RetrofitClient.getApiService();

        api.eliminarPrecio(idPrecio).enqueue(new Callback<PrecioResponse>() {
            @Override
            public void onResponse(Call<PrecioResponse> call, Response<PrecioResponse> response) {
                if (response.isSuccessful()) {

                    lista.remove(position);
                    notifyItemRemoved(position);

                    Toast.makeText(v.getContext(), "Precio eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Error del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PrecioResponse> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView descripcion, valor;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descripcion = itemView.findViewById(R.id.txtTipoBol);
            valor = itemView.findViewById(R.id.txtPrecioBol);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
