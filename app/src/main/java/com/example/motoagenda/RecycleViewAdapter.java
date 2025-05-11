package com.example.motoagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

    List<Pasajero> ListaPasajeros;
    Context context;

    public RecycleViewAdapter(List<Pasajero> listaPasajeros, Context context) {
        ListaPasajeros = listaPasajeros;
        this.context = context;
    }

    public void actualizarLista(List<Pasajero> nuevaLista) {
        this.ListaPasajeros = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.una_tarjeta_pasajero, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_id_pasajero.setText(String.valueOf(ListaPasajeros.get(position).getId()));
        holder.tv_nombre_pasajero.setText(ListaPasajeros.get(position).getNombre() + " " + ListaPasajeros.get(position).getApellido());
        holder.tv_genero_pasajero.setText(ListaPasajeros.get(position).getGenero());
    }

    @Override
    public int getItemCount() {
        return ListaPasajeros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nombre_pasajero, tv_id_pasajero, tv_genero_pasajero;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombre_pasajero = itemView.findViewById(R.id.tv_nombre_pasajero);
            tv_id_pasajero = itemView.findViewById(R.id.tv_id_pasajero);
            tv_genero_pasajero = itemView.findViewById(R.id.tv_genero_pasajero);
        }

    }

}
