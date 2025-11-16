package com.example.cineapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrecioAdapter extends RecyclerView.Adapter<PrecioAdapter.ViewHolder> {

    List<Precio> lista;

    public PrecioAdapter(List<Precio> lista) {
        this.lista = lista;
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
        Precio p = lista.get(position);

        holder.descripcion.setText(p.getDescripcion());
        holder.valor.setText("$ " + p.getValor());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView descripcion, valor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descripcion = itemView.findViewById(R.id.txtTipoBol);
            valor = itemView.findViewById(R.id.txtPrecioBol);
            // ❗ ¡ELIMINAR! ya NO existe en el XML
            // observacion = itemView.findViewById(R.id.txtObservacionBol);
        }
    }
}
