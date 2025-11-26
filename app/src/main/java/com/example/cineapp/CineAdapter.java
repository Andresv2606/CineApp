package com.example.cineapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.R;
import com.example.cineapp.Reservar;
import com.example.cineapp.RetrofitClient;
import com.example.cineapp.models.Cine;
import com.example.cineapp.models.HorarioResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CineAdapter extends RecyclerView.Adapter<CineAdapter.CineViewHolder> {

    private List<Cine> listaCines;
    private int idPelicula;
    private String nombrePelicula;
    public CineAdapter(List<Cine> lista, int idPelicula, String nombrePelicula) {
        this.listaCines = lista;
        this.idPelicula = idPelicula;
        this.nombrePelicula = nombrePelicula;
    }
    public CineAdapter(List<Cine> lista, int idPelicula) {
        this.listaCines = lista;
        this.idPelicula = idPelicula;
        this.nombrePelicula = "";
    }

    public void updateList(List<Cine> nuevos) {
        this.listaCines = nuevos;
        notifyDataSetChanged();
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
        holder.txtDireccion.setText("Dirección: " + cine.getDireccion());
        holder.txtTelefono.setText("Tel: " + cine.getTelefono());

        holder.layoutHorarios.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {

            if (holder.layoutHorarios.getVisibility() == View.VISIBLE) {
                holder.layoutHorarios.setVisibility(View.GONE);
                return;
            }

            holder.layoutHorarios.removeAllViews();

            TextView titulo = new TextView(v.getContext());
            titulo.setText("Horarios disponibles");
            titulo.setTextSize(16);
            titulo.setPadding(8, 8, 8, 16);
            titulo.setTextColor(0xFF000000);
            holder.layoutHorarios.addView(titulo);

            RetrofitClient.getApiService()
                    .getHorarios(idPelicula, cine.getId_cine())
                    .enqueue(new Callback<HorarioResponse>() {
                        @Override
                        public void onResponse(Call<HorarioResponse> call, Response<HorarioResponse> response) {

                            if (!response.isSuccessful() || response.body() == null) return;

                            List<HorarioResponse.Horario> horarios = response.body().getHorarios();

                            for (HorarioResponse.Horario h : horarios) {
                                TextView txt = new TextView(v.getContext());
                                txt.setText(h.getHora() + "  -  Sala " + h.getNombre_sala());
                                txt.setTextSize(15);
                                txt.setPadding(12, 6, 12, 6);
                                txt.setTextColor(0xFF444444);

                                txt.setOnClickListener(view -> {

                                    Intent intent = new Intent(view.getContext(), Reservar.class);

                                    // 🔹 Datos del horario
                                    intent.putExtra("id_horario", h.getId_horario());
                                    intent.putExtra("hora", h.getHora());
                                    intent.putExtra("sala", h.getNombre_sala());

                                    // 🔹 Datos del cine
                                    intent.putExtra("id_cine", cine.getId_cine());
                                    intent.putExtra("cine_nombre", cine.getNombre());

                                    // 🔹 Datos de la película
                                    intent.putExtra("id_pelicula", idPelicula);
                                    intent.putExtra("pelicula", nombrePelicula);

                                    view.getContext().startActivity(intent);
                                });

                                holder.layoutHorarios.addView(txt);
                            }

                            holder.layoutHorarios.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<HorarioResponse> call, Throwable t) { }
                    });

        });
    }

    @Override
    public int getItemCount() {
        return listaCines != null ? listaCines.size() : 0;
    }

    public static class CineViewHolder extends RecyclerView.ViewHolder {

        TextView txtCine, txtDireccion, txtTelefono;
        LinearLayout layoutHorarios;

        public CineViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCine = itemView.findViewById(R.id.txtCine);
            txtDireccion = itemView.findViewById(R.id.txtDireccionCine);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            layoutHorarios = itemView.findViewById(R.id.layoutHorarios);
        }
    }
}
