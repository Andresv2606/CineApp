package com.example.cineapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.PrecioAdapter;
import com.example.cineapp.R;
import com.example.cineapp.Cine;
import com.example.cineapp.Precio;

import java.util.List;

public class CineAdapter extends RecyclerView.Adapter<CineAdapter.CineViewHolder> {

    private List<Cine> listaCines;

    public CineAdapter(List<Cine> lista) {
        this.listaCines = lista;
    }

    @NonNull
    @Override
    public CineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cine, parent, false);
        return new CineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CineViewHolder holder, int position) {
        Cine cine = listaCines.get(position);

        holder.txtCine.setText(cine.getNombre());
        holder.txtDireccion.setText(cine.getDireccion());
        holder.txtTelefono.setText("Tel. " + cine.getTelefono());

        // ADAPTADOR INTERNO DE PRECIOS
        List<Precio> precios = cine.getPrecios();
        PrecioAdapter precioAdapter = new PrecioAdapter(precios);

        holder.rvPrecio.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvPrecio.setAdapter(precioAdapter);
    }

    @Override
    public int getItemCount() {
        return listaCines != null ? listaCines.size() : 0;
    }

    public static class CineViewHolder extends RecyclerView.ViewHolder {

        TextView txtCine, txtDireccion, txtTelefono;
        RecyclerView rvPrecio;
        Button btnVerPeliculas;

        public CineViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCine = itemView.findViewById(R.id.txtCine);
            txtDireccion = itemView.findViewById(R.id.txtDireccionCine);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            rvPrecio = itemView.findViewById(R.id.rvPrecio);
            btnVerPeliculas = itemView.findViewById(R.id.btnVerPeliculas);
        }
    }

    public void updateList(List<Cine> nuevos) {
        this.listaCines = nuevos;
        notifyDataSetChanged();
    }
}