package com.example.cineapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.Pelicula;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {
    private Context context;
    private List<Pelicula> lista;

    private OnEliminarClickListener onEliminarClickListener;
    public PeliculaAdapter(Context context, List<Pelicula> lista) {
        this.context = context;
        this.lista = lista;
    }

    // ---------------------------------------------------------
    //  INTERFAZ PARA EL BOTÓN ELIMINAR
    // ---------------------------------------------------------
    public interface OnEliminarClickListener {
        void onEliminarClick(int idPelicula);
    }

    public void setOnEliminarClickListener(OnEliminarClickListener listener) {
        this.onEliminarClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pelicula, parent, false);
        return new ViewHolder(view);
    }

    public void actualizarLista(List<Pelicula> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pelicula p = lista.get(position);

        holder.titulo.setText(p.getTitulo());
        holder.genero.setText(p.getGenero());
        holder.clasificacion.setText(p.getClasificacion());

        // Tu API NO trae lugar ni horario
        holder.lugar.setText("No disponible");
        holder.horario.setText("No disponible");

        int idPelicula = p.getId_pelicula();

        // ----------------------------
        // BOTÓN DETALLE
        // ----------------------------
        /*holder.btnDetalle.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetallePeliculaActivity.class);
            intent.putExtra("id_pelicula", idPelicula);
            v.getContext().startActivity(intent);
        });*/

        // ----------------------------
        // BOTÓN EDITAR
        // ----------------------------
        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditarPeliculaActivity.class);
            intent.putExtra("id_pelicula", idPelicula);
            v.getContext().startActivity(intent);
        });

        // ----------------------------
        // BOTÓN ELIMINAR
        // ----------------------------
        holder.btnEliminar.setOnClickListener(v -> {
            if (onEliminarClickListener != null) {
                onEliminarClickListener.onEliminarClick(idPelicula);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, genero, clasificacion, lugar, horario;
        ImageView imagen;
        Button btnDetalle, btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txtTituloPelicula);
            genero = itemView.findViewById(R.id.txtGenero);
            clasificacion = itemView.findViewById(R.id.txtClasificacion);
            lugar = itemView.findViewById(R.id.txtLugar);
            horario = itemView.findViewById(R.id.txtHorario);
            imagen = itemView.findViewById(R.id.imgPelicula);
            btnDetalle = itemView.findViewById(R.id.btnDetalle);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
