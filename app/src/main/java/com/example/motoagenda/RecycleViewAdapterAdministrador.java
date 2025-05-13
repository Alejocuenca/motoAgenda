package com.example.motoagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapterAdministrador extends RecyclerView.Adapter<RecycleViewAdapterAdministrador.MyViewHolder> {

    List<Administrador> ListaAdministradores;
    Context context;

    public RecycleViewAdapterAdministrador(List<Administrador> listaAdministradores, Context context) {
        ListaAdministradores = listaAdministradores;
        this.context = context;
    }
    public void actualizarLista(List<Administrador> nuevaLista) {
        this.ListaAdministradores = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.una_tarjeta_administrador, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_id_administrador.setText(String.valueOf(ListaAdministradores.get(position).getId()));
        holder.tv_nombre_administrador.setText(ListaAdministradores.get(position).getNombre() + " " + ListaAdministradores.get(position).getApellido());
        holder.tv_tipo_usuario.setText(ListaAdministradores.get(position).getTipoUsuario());

    }

    @Override
    public int getItemCount() {
        return ListaAdministradores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre_administrador, tv_id_administrador, tv_tipo_usuario;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombre_administrador = itemView.findViewById(R.id.tv_nombre_administrador);
            tv_id_administrador = itemView.findViewById(R.id.tv_id_administrador);
            tv_tipo_usuario = itemView.findViewById(R.id.tv_tipo_usuario);
        }

    }

}
